package ztysdmy.textsearch.model;

public abstract class Segment {
	
	private String value;

	public Segment(String value) {
		this.value = value;
	}

	// Utility methods to create Text Segments
	public static Document document(String value) {

		Document result = new Document(value);
		return result;
	}

	public static Sentence sentence(String value) {

		Sentence result = new Sentence(value);
		return result;
	}

	public String toString() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public static class Document extends Segment {

		public Document(String value) {
			super(value);
		}
		
	}
	
	public static class Sentence extends Segment {

		public Sentence(String value) {
			super(value);
		}
		
	}
	
}
