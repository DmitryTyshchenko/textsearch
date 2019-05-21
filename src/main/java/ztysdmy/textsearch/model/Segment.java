package ztysdmy.textsearch.model;

import java.util.function.Function;

public class Segment {

	public enum SegmentType {

		DOCUMENT(null), PARAGRAPH(null), SENTENCE(segment -> {
			return TermsVectorBuilder.build(segment);
		});

		private SegmentType(Function<Segment, TermsVector> function) {
			this.function = function;
		}

		private Function<Segment, TermsVector> function;

		private Function<Segment, TermsVector> termsVectorBuildStrategy() {

			return function;
		}
		
		//private final Fucntion<Segment, TermsVector> emptyTermsVector
	}

	private final SegmentType segmentType;

	private final String value;

	public Segment(String value, SegmentType segmentType) {

		this.segmentType = segmentType;
		this.value = value;
	}

	// Utility methods to create Text Segments
	public static Segment document(String value) {

		Segment result = new Segment(value, SegmentType.DOCUMENT);
		return result;
	}

	public static Segment sentence(String value) {

		Segment result = new Segment(value, SegmentType.SENTENCE);
		return result;
	}

	public String toString() {
		return value;
	}

	public SegmentType segmentType() {
		return segmentType;
	}
	
	public TermsVector buildTermsVector() {
		
		return segmentType.termsVectorBuildStrategy().apply(this);
	}
}
