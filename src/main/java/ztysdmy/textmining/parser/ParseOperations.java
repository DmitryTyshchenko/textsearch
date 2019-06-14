package ztysdmy.textmining.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import ztysdmy.textmining.model.Text;

public class ParseOperations {

	private ParseOperations() {
	}

	public static final Function<Text, Text> removeHtmlTagsOperation = segment -> removeHtmlTags(segment);

	public static final Function<Text, Collection<Text>> splitToSentencesOperation = segment -> splitToSentences(
			segment);

	private static Text removeHtmlTags(Text segment) {
		var htmlTagsRemover = new SimpleHTMLTagsRemover();
		return htmlTagsRemover.removeHtmlTags(segment);
	}

	private static Collection<Text> splitToSentences(Text input) {

		SentenceSplitterator sentenceSpliterator = new SimpleSentenceSpliterator();
		return sentenceSpliterator.parseSentences(input).stream().collect(Collectors.toList());
	}

	// TODO: think to move it into separate files
	@FunctionalInterface
	interface HTMLTagsRemover {

		Text removeHtmlTags(Text input);
	}

	@FunctionalInterface
	interface SentenceSplitterator {

		Collection<Text> parseSentences(Text input);
	}

	static class SimpleHTMLTagsRemover implements HTMLTagsRemover {

		private static final Pattern REMOVE_TAGS = Pattern.compile("<.+?>");

		@Override
		public Text removeHtmlTags(Text input) {
			var matcher = REMOVE_TAGS.matcher(input.toString());
			input.setValue(matcher.replaceAll(""));
			return input;
		}
	}

	static class SimpleSentenceSpliterator implements SentenceSplitterator {

		private static final Pattern SENTENCE_END = Pattern.compile("(!+|\\?+|\\.+)\\s");

		@Override
		public Collection<Text> parseSentences(Text input) {
			Collection<Text> collector = new ArrayList<>();
			parseAndCollectSentences(input, collector);
			return collector;
		}

		private void parseAndCollectSentences(Text input, Collection<Text> collector) {

			String segmentValue = input.toString();

			var matcher = SENTENCE_END.matcher(segmentValue);

			if (matcher.find()) {
				var index = matcher.start() + (matcher.end() - matcher.start());
				var sentence = segmentValue.substring(0, index);
				//remove space symbol at the end
				sentence = sentence.stripTrailing();
				//collector.add(new Segment(sentence, SegmentType.SENTENCE));
				collector.add(Text.from(sentence));
				var remaining = segmentValue.substring(index);
				//parseAndCollectSentences(new Segment(remaining, input.segmentType()), collector);
				parseAndCollectSentences(Text.from(remaining), collector);
			} else {
				collector.add(input);
			}
		}
	}
}
