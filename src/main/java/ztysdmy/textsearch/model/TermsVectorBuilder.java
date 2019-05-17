package ztysdmy.textsearch.model;

import java.util.Set;
import java.util.function.Function;

public class TermsVectorBuilder {

	private TermsVectorBuilder() {
	}

	public static TermsVector build(Segment segment) {
		return build(segment, 3);
	}

	public static TermsVector build(Segment segment, int complexity) {

		TermsVector result = new TermsVector(segment);

		String candidates[] = splitSegmentValuesToWords.andThen(normilizer).apply(segment);

		for (int i = 0; i < candidates.length; i++) {
			var value = candidates[i];
			result.createOrUpdateTerm(value);
			createComplexTerms(result, candidates, complexity, i);
		}

		return result;
	}

	/**
	 * Creates complex Terms. For example for array ['a','b','c'] for word 'a' next Terms will be created with complexity 3:
	 * 'a b', 'a b c' 
	 * @param result
	 * @param candidates
	 * @param complexity
	 * @param currentIndex
	 */
	private static void createComplexTerms(TermsVector result, String[] candidates, int complexity, int currentIndex) {

		var offset = currentIndex + 1;
		var complexityOffset = 0;

		var stringBuilder = new StringBuilder();
		stringBuilder.append(candidates[currentIndex]);
		while ((complexityOffset != complexity) && (offset < candidates.length)) {
			result.createOrUpdateTerm(createComplexTerm(stringBuilder, candidates, offset));
			offset++;
			complexityOffset++;
		}

	}

	private static String createComplexTerm(StringBuilder stringBuilder, String[] candidates, int offset) {
		stringBuilder.append(" ");
		stringBuilder.append(candidates[offset]);
		return stringBuilder.toString();
	}

	// TODO: consider to move it in different package
	final static Function<Segment, String[]> splitSegmentValuesToWords = segment -> segment.toString().split("\\s");

	final static Function<String, String> lowerCaseNormilizer = value -> value.toLowerCase();

	//removes symbols like ',' ':' etc
	private static final Set<Character> PUNCTUATION_VALUES = Set.of(',', '.', ':', '!', '?', ';');
	
	final static Function<String, String> punctuationNormilizer = value -> {
		
		char last_character = value.charAt((value.length()-1));
		
		if (!PUNCTUATION_VALUES.contains(last_character)) {
			
			return value;
		}
		return removeLastCharacter(value);
	};
	
	private static String removeLastCharacter(String value) {
		return value.substring(0, (value.length()-1));
	}
	
	final static Function<String[], String[]> normilizer = arrayOfStrings -> {
		for (int i = 0; i < arrayOfStrings.length; i++) {
			arrayOfStrings[i] = lowerCaseNormilizer.andThen(punctuationNormilizer).apply(arrayOfStrings[i]);
		}
		return arrayOfStrings;
	};

}
