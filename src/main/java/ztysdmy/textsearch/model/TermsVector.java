package ztysdmy.textsearch.model;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.BiFunction;

import ztysdmy.textsearch.distance.TanimotoDistance;

public class TermsVector {

	final HashMap<String, Term> terms = new HashMap<>();

	private final Segment segment;

	TermsVector(Segment segment) {

		this.segment = segment;
	}

	public HashMap<String, Term> terms() {
		return this.terms;
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
	 * Returns distance of This to Input TermsVector By default calculates distance
	 * between two TermsVector by using Tanimoto metric
	 * 
	 * @param input
	 * @return
	 */
	public double distance(TermsVector input) {
		return tanimotoDistance().apply(this, input);
	}

	/**
	 * Return distance of This to Input TermsVector
	 * 
	 * @param input
	 * @param strategy - Strategy of distance calculation
	 * @return
	 */
	public double distance(TermsVector input, BiFunction<TermsVector, TermsVector, Double> strategy) {

		return strategy.apply(this, input);
	}

	//Tanimoto Metric implementation
	private static final BiFunction<TermsVector, TermsVector, Double> tanimotoDistance() {

		return new TanimotoDistance();
	}

	public static TermsVector emptyTermsVector(Segment segment) {
		return new TermsVector(segment);
	}
}
