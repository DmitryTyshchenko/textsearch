package ztysdmy.textsearch.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

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
		return tanimotoMetric().apply(this, input);
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
	private static final BiFunction<TermsVector, TermsVector, Double> tanimotoMetric() {

		return (tV1, tV2) -> {

			var x = tV1.terms.keySet().size();
			var y = tV2.terms.keySet().size();

			Set<String> termVector1 = new HashSet<>();
			termVector1.addAll(tV1.terms.keySet());
			Set<String> termVector2 = new HashSet<>();
			termVector2.addAll(tV2.terms.keySet());

			termVector1.retainAll(termVector2);

			var common = termVector1.size();

			var nominator = x + y - 2.d * common;
			var denominator =  x + y - common;
			
			var distance =  nominator/denominator;
			//convert result to probability
			return 1.d - distance;
		};
	}

}
