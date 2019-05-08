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
		var withoutTagsValue = htmlTagsRemover.removeHtmlTags(segment.toString());
		return new Segment(withoutTagsValue, segment.segmentType());
	}

	private static Collection<Segment> splitToSentences(Segment input) {

		SentenceSplitterator sentenceSpliterator = new SimpleSentenceSpliterator();
		return sentenceSpliterator.parseSentences(input.toString()).stream()
				.map(sentence -> new Segment(sentence, input.segmentType())).collect(Collectors.toList());
	}

	// TODO: think to move it into separate files
	interface HTMLTagsRemover {

		String removeHtmlTags(String input);
	}

	interface SentenceSplitterator {

		Collection<String> parseSentences(String input);
	}

	static class SimpleHTMLTagsRemover implements HTMLTagsRemover {

		private static final Pattern REMOVE_TAGS = Pattern.compile("<.+?>");

		@Override
		public String removeHtmlTags(String input) {
			var matcher = REMOVE_TAGS.matcher(input);
			return matcher.replaceAll("");
		}
	}

	static class SimpleSentenceSpliterator implements SentenceSplitterator {

		private static final Pattern SENTENCE_END = Pattern.compile("!");

		@Override
		public Collection<String> parseSentences(String input) {
			Collection<String> collector = new ArrayList<>();
			parseAndCollectSentences(input, collector);
			return collector;
		}

		private void parseAndCollectSentences(String input, Collection<String> collector) {

			var matcher = SENTENCE_END.matcher(input);

			if (matcher.find()) {
				var index = matcher.start() + (matcher.end() - matcher.start());
				var sentence = input.substring(0, index);
				collector.add(sentence);
				var remaining = input.substring(index);
				parseAndCollectSentences(remaining, collector);
			} else {
				collector.add(input);
			}
		}
	}
}
