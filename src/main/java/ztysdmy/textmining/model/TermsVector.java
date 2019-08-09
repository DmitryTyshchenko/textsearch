package ztysdmy.textmining.model;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.Optional;

public class TermsVector {

	final HashSet<Term> terms = new HashSet<>();

	TermsVector() {
	}

	public Set<Term> terms() {
		return this.terms;
	}

	public Optional<Term> getTerm(Term term) {
		return this.terms.stream().filter(t -> t.equals(term)).findFirst();
	}

	public void addTerm(String termValue) {

		var term = new Term(termValue);
		this.terms.add(term);
	}

	/**
	 * @param input
	 * @return
	 */
	public double eval(TermsVector input, BiFunction<TermsVector, TermsVector, Double> evalFunction) {
		return evalFunction.apply(this, input);
	}

	public double eval(Function<TermsVector, Double> evalFunction) {
		return evalFunction.apply(this);
	}

}
