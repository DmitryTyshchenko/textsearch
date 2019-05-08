package ztysdmy.textsearch.model;

public class Segment {
	
	public static enum SegmentType {
		
		DOCUMENT, PARAGRAPH, SENTENCE;
	}

	private final SegmentType segmentType;
	
	private final String value;
	
	Segment(String value, SegmentType segmentType) {
		
		this.segmentType = segmentType;
		this.value = value;
	}
	
	//Utility methods to create Text Segments
	public static Segment document(String value) {
		
		Segment result = new Segment(value, SegmentType.DOCUMENT);
		return result;
	}
	
	public static Segment sentence(String value) {
		
		Segment result = new Segment(value, SegmentType.SENTENCE);
		return result;
	}
}
