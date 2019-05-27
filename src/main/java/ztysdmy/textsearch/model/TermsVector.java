package ztysdmy.textsearch.model;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.BiFunction;

import ztysdmy.textsearch.distance.TanimotoDistance;

public class TermsVector {

	final HashMap<String, Term> terms = new HashMap<>();
	
	private  BiFunction<TermsVector, TermsVector, Double> evalFunction;

	TermsVector() {

		evalFunction = new  TanimotoDistance();
		
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
	 * between two TermsVector 
	 * 
	 * @param input
	 * @return
	 */
	public double eval(TermsVector input) {
		return evalFunction().apply(this, input);
	}

	
	private BiFunction<TermsVector, TermsVector, Double> evalFunction() {

		return this.evalFunction;
	}

	public static TermsVector emptyTermsVector() {
		return new TermsVector();
	}
}
