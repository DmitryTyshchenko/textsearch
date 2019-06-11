package ztysdmy.textsearch.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import ztysdmy.textsearch.model.TextSegment;

public class ParseOperations {

	private ParseOperations() {
	}

	public static final Function<TextSegment, TextSegment> removeHtmlTagsOperation = segment -> removeHtmlTags(segment);

	public static final Function<TextSegment, Collection<TextSegment>> splitToSentencesOperation = segment -> splitToSentences(
			segment);

	private static TextSegment removeHtmlTags(TextSegment segment) {
		var htmlTagsRemover = new SimpleHTMLTagsRemover();
		return htmlTagsRemover.removeHtmlTags(segment);
	}

	private static Collection<TextSegment> splitToSentences(TextSegment input) {

		SentenceSplitterator sentenceSpliterator = new SimpleSentenceSpliterator();
		return sentenceSpliterator.parseSentences(input).stream().collect(Collectors.toList());
	}

	// TODO: think to move it into separate files
	@FunctionalInterface
	interface HTMLTagsRemover {

		TextSegment removeHtmlTags(TextSegment input);
	}

	@FunctionalInterface
	interface SentenceSplitterator {

		Collection<TextSegment> parseSentences(TextSegment input);
	}

	static class SimpleHTMLTagsRemover implements HTMLTagsRemover {

		private static final Pattern REMOVE_TAGS = Pattern.compile("<.+?>");

		@Override
		public TextSegment removeHtmlTags(TextSegment input) {
			var matcher = REMOVE_TAGS.matcher(input.toString());
			input.setValue(matcher.replaceAll(""));
			return input;
		}
	}

	static class SimpleSentenceSpliterator implements SentenceSplitterator {

		private static final Pattern SENTENCE_END = Pattern.compile("(!+|\\?+|\\.+)\\s");

		@Override
		public Collection<TextSegment> parseSentences(TextSegment input) {
			Collection<TextSegment> collector = new ArrayList<>();
			parseAndCollectSentences(input, collector);
			return collector;
		}

		private void parseAndCollectSentences(TextSegment input, Collection<TextSegment> collector) {

			String segmentValue = input.toString();

			var matcher = SENTENCE_END.matcher(segmentValue);

			if (matcher.find()) {
				var index = matcher.start() + (matcher.end() - matcher.start());
				var sentence = segmentValue.substring(0, index);
				//remove space symbol at the end
				sentence = sentence.stripTrailing();
				//collector.add(new Segment(sentence, SegmentType.SENTENCE));
				collector.add(TextSegment.from(sentence));
				var remaining = segmentValue.substring(index);
				//parseAndCollectSentences(new Segment(remaining, input.segmentType()), collector);
				parseAndCollectSentences(TextSegment.from(remaining), collector);
			} else {
				collector.add(input);
			}
		}
	}
}
