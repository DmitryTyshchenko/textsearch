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

	int complexity;
	int totalFacts;

	public NaiveBaeys() {
		this(1);
	}

	public NaiveBaeys(int complexity) {
		this.complexity = complexity;
	}

	public void setTotalFacts(int totalFacts) {
		this.totalFacts = totalFacts;
	}

	public int totalFacts() {
		return totalFacts;
	}

	public void addTarget(Target<T> target) {

		classes.compute(target, (k, v) -> {

			if (v == null)
				return 1;
			return ++v;
		});
	}

	@Override
	public LikelihoodResult<T> likelihood(Fact<T> input) {
		TermsVector factTerms = TermsVectorBuilder.build(input, this.complexity);

		LikelihoodResult<T> IDENTITY = new LikelihoodResult<>(null, 0.d);

		LikelihoodResult<T> result = classes.keySet().stream().map(target -> pcx.apply(target).apply(factTerms))
				.reduce(IDENTITY, (a, b) -> chooseMax(a, b));

		return result;
	}

	LikelihoodResult<T> chooseMax(LikelihoodResult<T> a, LikelihoodResult<T> b) {

		if (b.probability() >= a.probability()) {
			return b;
		}
		return a;

	}

	static class TermStatistics {
		HashMap<Target<?>, Integer> inTheClass = new HashMap<>();

		void incrementOccuriens(Target<?> target) {

			inTheClass.compute(target, (k, v) -> {
				if (v == null) {
					return 1;
				}
				return ++v;
			});

			totalOccuriences++;
		}

		int termInClassOccurencies(Target<?> target) {
			var result = inTheClass.get(target);
			// Laplace smoothing
			if (result == null)
				return 1;
			return result;
		}

		int totalOccuriences() {
			return this.totalOccuriences;
		}

		private int totalOccuriences;
	}

	TermStatistics termStatitics(Term term) {

		var result = this.termsStatistics.get(term);

		// Laplace smoothing
		if (result == null) {

			result = new TermStatistics();
			result.totalOccuriences = 1;
		}

		return result;
	}

	Function<TermsVector, Double> denominator = tv -> {
		return tv.terms().stream().map(x -> termStatitics(x)).map(x -> (x.totalOccuriences() * 1.d) / totalFacts())
				.reduce(1.d, (x, y) -> x * y);
	};

	Function<Target<?>, Double> py = target -> classes.get(target) * 1.d / totalFacts();

	Function<Target<?>, Function<Term, Double>> pxc = target -> term -> pxc(target, term);

	private double pxc(Target<?> target, Term term) {
		var termStatistic = termStatitics(term);
		// calculate p(x|c)
		var termInClassOccurencies = termStatistic.termInClassOccurencies(target);
		var pxc = (termInClassOccurencies * 1.d) / termStatistic.totalOccuriences();
		return pxc;
	}

	Function<Target<?>, Function<TermsVector, Double>> numenator = target -> tv -> {

		var z = tv.terms().stream().map(pxc.apply(target)).reduce(1.d, (x, y) -> x * y);

		return z * py.apply(target);

	};

	Function<Target<T>, Function<TermsVector, LikelihoodResult<T>>> pcx = target -> termsVector -> {
		var probability = (numenator.apply(target).apply(termsVector) * 1.d) / denominator.apply(termsVector);

		return new LikelihoodResult<T>(target, probability);
	};
}
