package ztysdmy.textmining.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import ztysdmy.functional.tailcall.TailCall;

import static ztysdmy.functional.tailcall.TailCallUtility.*;


public class ParseOperations {

	private ParseOperations() {
	}

	public static final Function<String, String> removeHtmlTagsOperation = segment -> removeHtmlTags(segment);

	public static final Function<String, Collection<String>> splitToSentencesOperation = segment -> splitToSentences(
			segment);

	private static String removeHtmlTags(String segment) {
		var htmlTagsRemover = new SimpleHTMLTagsRemover();
		return htmlTagsRemover.removeHtmlTags(segment);
	}

	private static Collection<String> splitToSentences(String input) {

		SentenceSplitterator sentenceSpliterator = new SimpleSentenceSpliterator();
		return sentenceSpliterator.parseSentences(input).stream().collect(Collectors.toList());
	}

	// TODO: think to move it into separate files
	@FunctionalInterface
	interface HTMLTagsRemover {

		String removeHtmlTags(String input);
	}

	@FunctionalInterface
	interface SentenceSplitterator {

		Collection<String> parseSentences(String input);
	}

	static class SimpleHTMLTagsRemover implements HTMLTagsRemover {

		private static final Pattern REMOVE_TAGS = Pattern.compile("<.+?>");

		@Override
		public String removeHtmlTags(String input) {
			var matcher = REMOVE_TAGS.matcher(input.toString());
			return matcher.replaceAll("");
		}
	}

	static class SimpleSentenceSpliterator implements SentenceSplitterator {

		private static final Pattern SENTENCE_END = Pattern.compile("(!+|\\?+|\\.+)\\s");

		@Override
		public Collection<String> parseSentences(String input) {
			Collection<String> collector = new ArrayList<>();
			parseAndCollectSentences(input, collector).invoke();
			return collector;
		}

		private TailCall<Collection<String>> parseAndCollectSentences(String input, Collection<String> collector) {

			String segmentValue = input.toString();

			var matcher = SENTENCE_END.matcher(segmentValue);

			if (!matcher.find()) {
				collector.add(input);
				return done(collector);
			} else {
				var index = matcher.start() + (matcher.end() - matcher.start());
				var sentence = segmentValue.substring(0, index);
				// remove space symbol at the end
				sentence = sentence.stripTrailing();
				// collector.add(new Segment(sentence, SegmentType.SENTENCE));
				collector.add(sentence);
				var remaining = segmentValue.substring(index);
				// parseAndCollectSentences(new Segment(remaining, input.segmentType()),
				// collector);
				return call(()->parseAndCollectSentences(remaining, collector));
			}
		}
		
	}
}
