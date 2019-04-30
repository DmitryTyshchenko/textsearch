package ztysdmy.textsearch.model;

import java.util.HashMap;
import java.util.function.BiFunction;

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

}
