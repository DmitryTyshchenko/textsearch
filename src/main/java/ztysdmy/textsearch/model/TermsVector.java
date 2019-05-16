package ztysdmy.textsearch.model;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class TermsVector {

	final HashMap<String, Term> terms = new HashMap<>();

	private final Segment segment;

	public TermsVector(Segment segment) {

		this.segment = segment;
	}

	public void createOrUpdateTerm(String termValue) {

		var term = new Term(termValue);

		BiFunction<String, Term, Term> mappingFunction = (k, v) -> {
			if (v == null) {
				return term;
			}
			v.increment();
			return v;
		};

		this.terms.compute(termValue, mappingFunction);
	}

	public Optional<Term> getTerm(String value) {
		return Optional.ofNullable(terms.get(value));
	}
	
	/**
	 * Returns distance of this TermsVector to input TermsVector
	 * Uses default distance calculation strategy 
	 * @param input
	 * @return
	 */
	public double distance(TermsVector input) {
		
		return 0.d;
	}
	
	/**
	 * 
	 * @param input
	 * @param stratigy
	 * @return
	 */
	public double distance(TermsVector input, BiFunction<TermsVector, TermsVector, Double> strategy) {
		
		return strategy.apply(this, input);
	}
	
	/**private static final Function<TermsVector, Double> defaultDistanceStratigy() {
		
		return termsVector -> 
	}**/
	
}
