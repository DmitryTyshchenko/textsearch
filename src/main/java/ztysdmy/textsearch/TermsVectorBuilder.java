package ztysdmy.textsearch;

import ztysdmy.textsearch.model.Segment;
import ztysdmy.textsearch.model.TermsVector;

public class TermsVectorBuilder {

	private TermsVectorBuilder() {
	}

	public static TermsVector build(Segment segment) {
		return build(segment, 3);
	}

	public static TermsVector build(Segment segment, int complexity) {

		TermsVector result = new TermsVector(segment);

		String candidates[] = segment.toString().split("\\s");
		
		for (int i = 0; i < candidates.length; i++) {
	    	var offset = i+1;
	    	var compexityOffset = 0;
			var value = candidates[i];
			result.createOrUpdateTerm(value);
			var stringBuilder = new StringBuilder();
			stringBuilder.append(value);
			while ((compexityOffset != complexity) && (offset < candidates.length)) {
				stringBuilder.append(" ");
				stringBuilder.append(candidates[offset]);
				result.createOrUpdateTerm(stringBuilder.toString());
				offset++;
				compexityOffset++;
			}
		}

		return result;
	}
}
