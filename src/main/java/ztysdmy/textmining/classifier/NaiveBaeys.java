package ztysdmy.textmining.classifier;

import java.util.HashMap;
import java.util.function.Function;

import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.LikelihoodResult;
import ztysdmy.textmining.model.Target;
import ztysdmy.textmining.model.Term;
import ztysdmy.textmining.model.TermsVector;
import ztysdmy.textmining.model.TermsVectorBuilder;

public class NaiveBaeys<T> implements Classifier<T> {

	HashMap<Term, TermStatistics> termsStatistics = new HashMap<>();

	// keeps Target classes and their occurrences
	HashMap<Target<T>, Integer> classes = new HashMap<>();

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
		TermsVector factTerms = TermsVectorBuilder.build(input, this.complexity);

		LikelihoodResult<T> IDENTITY = new LikelihoodResult<>(null, 0.d);
		
		LikelihoodResult<T> result =  classes.keySet().stream().map(target->pcx.apply(target).apply(factTerms)).reduce(IDENTITY,(a,b)->{
	      if (b.probability()>=a.probability()) {
	    	  return b;
	      }
	      return a;
		});
	
		return result;
	}

	static class TermStatistics {
		HashMap<Target<?>, Integer> inTheClass = new HashMap<>();
		int totalOccuriences;
	}

	private TermStatistics termStatitics(Term term) {

		return this.termsStatistics.get(term);
	}

	Function<TermsVector, Double> denominator = tv -> {
		return tv.terms().stream().map(x -> termStatitics(x)).map(x -> (x.totalOccuriences * 1.d) / totalFacts())
				.reduce(1.d, (x, y) -> x * y);
	};

	Function<Target<?>, Double> py = target -> classes.get(target) * 1.d / totalFacts();

	Function<Target<?>, Function<Term, Double>> pxc = target -> term -> pxc(target, term);

	private double pxc(Target<?> target, Term term) {
		var termStatistic = termsStatistics.get(term);
		// calculate p(x|c)
		var termInClassOccurencies = termStatistic.inTheClass.get(target);
		var pxc = (termInClassOccurencies * 1.d) / termStatistic.totalOccuriences;
		return pxc;
	}

	Function<Target<?>, Function<TermsVector, Double>> numenator = target -> tv -> {

		var z = tv.terms().stream().map(pxc.apply(target)).reduce(1.d, (x, y) -> x * y);

		return z * py.apply(target);

	};
	
	Function<Target<T>,Function<TermsVector, LikelihoodResult<T>>> pcx = target->termsVector-> {
		var probability = (numenator.apply(target).apply(termsVector)*1.d)/denominator.apply(termsVector);
		
		return new LikelihoodResult<T>(target, probability);
	};
}
