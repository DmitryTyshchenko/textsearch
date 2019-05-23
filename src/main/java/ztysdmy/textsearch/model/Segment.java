package ztysdmy.textsearch.model;

public class Segment {
	
	private String value;

	public Segment(String value) {
		this.value = value;
	}

	// Utility methods to create Text Segments
	public static Segment from(String value) {

		Segment result = new Segment(value);
		return result;
	}


	public String toString() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
}
