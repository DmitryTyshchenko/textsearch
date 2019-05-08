package ztysdmy.textsearch.parser;

import java.util.function.Function;
import java.util.regex.Pattern;

import ztysdmy.textsearch.model.Segment;

public class ParseOperations {

	private ParseOperations() {
	}

	public static final Function<Segment, Segment> removeHtmlTagsOperation = segment -> removeHtmlTags(segment);

	private static Segment removeHtmlTags(Segment segment) {

		var htmlTagsRemover = new SimpleHTMLTagsRemover();
		var withoutTagsValue = htmlTagsRemover.removeHtmlTags(segment.toString());
		return new Segment(withoutTagsValue, segment.segmentType());
	}

	interface HTMLTagsRemover {

		String removeHtmlTags(String value);
	}

	static class SimpleHTMLTagsRemover implements HTMLTagsRemover {

		private static final Pattern REMOVE_TAGS = Pattern.compile("<.+?>");

		public String removeHtmlTags(String string) {
			var m = REMOVE_TAGS.matcher(string);
			return m.replaceAll("");
		}
	}
}
