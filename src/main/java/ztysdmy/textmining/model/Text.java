package ztysdmy.textmining.model;

public class Text implements TextProvider {

	private String value;

	public Text(String value) {
		this.value = value;
	}

	// Utility methods to create Text Segments
	public static Text from(String value) {

		Text result = new Text(value);
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
