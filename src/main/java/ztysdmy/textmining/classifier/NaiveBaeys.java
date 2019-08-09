package ztysdmy.textmining.classifier;

import java.util.HashMap;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.LikelihoodResult;
import ztysdmy.textmining.model.Target;
import ztysdmy.textmining.model.Term;
import ztysdmy.textmining.model.TermsVector;
import ztysdmy.textmining.model.TermsVectorBuilder;

public class NaiveBaeys<T> implements Classifier<T> {

	HashMap<Term, TermStatistics> termsStatistics = new HashMap<>();

	// keeps Target classes and their probabilities
	HashMap<Target<T>, Double> classes = new HashMap<>();

	int complexity = 1;
	int totalFacts;
	
	public void setTotalFacts(int totalFacts) {
		this.totalFacts = totalFacts;
	}
	
	public int totalFacts() {
		return totalFacts;
	}

	@Override
	public LikelihoodResult<T> likelihood(Fact<T> input) {
		// TODO Auto-generated method stub
		TermsVector factTerms = TermsVectorBuilder.build(input, this.complexity);

		// classes.keySet().

		return null;
	}

	/**
	 * Implements p(c|x)=p(x|c)p(c)/p(x)
	 */
	private LikelihoodResult<T> likelihood(TermsVector factTerms, Target<T> target) {
		var classProbability = classes.get(target);

		factTerms.eval(tv -> 1.d);

		return null;
	}

	private Function<Target<T>, Function<TermsVector, Double>> func1 = target -> termsVector -> {

		BiFunction<Double, Double, Double> multiplication = (d1, d2) -> d1 * d2;

		Iterator<Term> terms = termsVector.terms().iterator();
		while (terms.hasNext()) {
			multiplication = createMultiplicationChain(multiplication, target, terms.next());
		}
		return multiplication.apply(1.d, 1.d);
	};

	private BiFunction<Double, Double, Double> createMultiplicationChain(BiFunction<Double, Double, Double> multiplication,
			Target<T> target, Term term) {

		var termStatistic = termsStatistics.get(term);
		// calculate p(x|c)
		var termInClassOccurencies = termStatistic.inTheClass.get(target);
		var termByClassProbability = (termInClassOccurencies * 1.d) / termStatistic.totalOccuriences;

		return multiplication.andThen(prev -> prev * termByClassProbability);

	}

	static class TermStatistics {

		HashMap<Target<?>, Integer> inTheClass = new HashMap<>();

		int totalOccuriences;

	}
	
	private TermStatistics termStatitics(Term term) {
		
		return this.termsStatistics.get(term);
	}

	Function<TermsVector, Double> denominator = tv-> {
		return tv.terms().stream().map(x->termStatitics(x)).map(x->(x.totalOccuriences*1.d)/totalFacts()).reduce(1.d, (x,y)->x*y);
	};
	
	
}
