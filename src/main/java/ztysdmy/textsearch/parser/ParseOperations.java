package ztysdmy.textsearch.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import ztysdmy.textsearch.model.Segment;

public class ParseOperations {

	private ParseOperations() {
	}

	public static final Function<Segment, Segment> removeHtmlTagsOperation = segment -> removeHtmlTags(segment);

	public static final Function<Segment, Collection<Segment>> spitToSentencesOperation = segment -> splitToSentences(
			segment);

	private static Segment removeHtmlTags(Segment segment) {
		var htmlTagsRemover = new SimpleHTMLTagsRemover();
		return htmlTagsRemover.removeHtmlTags(segment);
	}

	private static Collection<Segment> splitToSentences(Segment input) {

		SentenceSplitterator sentenceSpliterator = new SimpleSentenceSpliterator();
		return sentenceSpliterator.parseSentences(input).stream().collect(Collectors.toList());
	}

	// TODO: think to move it into separate files
	@FunctionalInterface
	interface HTMLTagsRemover {

		Segment removeHtmlTags(Segment input);
	}

	@FunctionalInterface
	interface SentenceSplitterator {

		Collection<Segment> parseSentences(Segment input);
	}

	static class SimpleHTMLTagsRemover implements HTMLTagsRemover {

		private static final Pattern REMOVE_TAGS = Pattern.compile("<.+?>");

		@Override
		public Segment removeHtmlTags(Segment input) {
			var matcher = REMOVE_TAGS.matcher(input.toString());
			return new Segment(matcher.replaceAll(""), input.segmentType());
		}
	}

	static class SimpleSentenceSpliterator implements SentenceSplitterator {

		private static final Pattern SENTENCE_END = Pattern.compile("(!+|\\?+|\\.+)\\s");

		@Override
		public Collection<Segment> parseSentences(Segment input) {
			Collection<Segment> collector = new ArrayList<>();
			parseAndCollectSentences(input, collector);
			return collector;
		}

		private void parseAndCollectSentences(Segment input, Collection<Segment> collector) {

			String segmentValue = input.toString();

			var matcher = SENTENCE_END.matcher(segmentValue);

			if (matcher.find()) {
				var index = matcher.start() + (matcher.end() - matcher.start());
				var sentence = segmentValue.substring(0, index);
				//remove space symbol at the end
				sentence = sentence.stripTrailing();
				collector.add(new Segment(sentence, input.segmentType()));
				var remaining = segmentValue.substring(index);
				parseAndCollectSentences(new Segment(remaining, input.segmentType()), collector);
			} else {
				collector.add(input);
			}
		}
	}
}
