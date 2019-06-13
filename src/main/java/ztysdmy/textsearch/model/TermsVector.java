package ztysdmy.textsearch.model;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.BiFunction;

public class TermsVector {

	final HashMap<String, Term> terms = new HashMap<>();
	

	TermsVector() {
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
	 * @param input
	 * @return
	 */
	public double eval(TermsVector input, BiFunction<TermsVector, TermsVector, Double> evalFunction) {
		return evalFunction.apply(this, input);
	}
	
}
