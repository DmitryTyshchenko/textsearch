package ztysdmy.textsearch.model;

public class TextSegment implements TextProvider {

	private String value;

	public TextSegment(String value) {
		this.value = value;
	}

	// Utility methods to create Text Segments
	public static TextSegment from(String value) {

		TextSegment result = new TextSegment(value);
		return result;
	}

	public String toString() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String text() {
		return toString();
	}

}
