package ztysdmy.textmining.classifier;

import java.util.HashMap;
import java.util.function.Consumer;
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
		this(0);
	}

	public NaiveBaeys(int complexity) {
		this.complexity = complexity;
	}

	// used only in Tests; consider to remove it
	void setTotalFacts(int totalFacts) {
		this.totalFacts = totalFacts;
	}

	int totalFacts() {
		return this.totalFacts;
	}

	void targetOccurrencies(Target<T> target) {

		classes.compute(target, (k, v) -> {

			if (v == null)
				return 1;
			return ++v;
		});
	}

	private void increaseTotalFacts() {	
		totalFacts++;		
	}
	
	public void collectFactStatistics(Fact<T> fact) {
		increaseTotalFacts();
		var target = fact.target().orElseThrow(() -> new RuntimeException("Fact without target"));
		targetOccurrencies(target);
		var terms = TermsVectorBuilder.build(fact, this.complexity);
		var consumer = computeTermStatistics.apply(target);
		terms.terms().forEach(consumer);
	}

	Function<Target<T>, Consumer<Term>> computeTermStatistics = target -> term -> {

		termsStatistics.compute(term, (k,v)->{
			
			if (v==null)
				v = new TermStatistics();
			
			v.update(target);
			return v;
		});
	};

	@Override
	public LikelihoodResult<T> likelihood(Fact<T> input) {
		TermsVector factTerms = filterTermsWithoutStatistics(TermsVectorBuilder.build(input, this.complexity));

		LikelihoodResult<T> IDENTITY = new LikelihoodResult<>(null, 0.d);

		LikelihoodResult<T> result = classes.keySet().stream().map(target -> pcx.apply(target).apply(factTerms))
				.reduce(IDENTITY, (a, b) -> chooseMax(a, b));

		return result;
	}
	
	//TODO consider to apply laplace smoothing instead
	private TermsVector filterTermsWithoutStatistics(TermsVector termsVector) {
		TermsVector newTermsVector = new TermsVector();
		
		termsVector.terms().stream().forEach(term->{
			if (termsStatistics.get(term)!=null)
				newTermsVector.addTerm(term);
		});
		
		return newTermsVector;
	}

	LikelihoodResult<T> chooseMax(LikelihoodResult<T> a, LikelihoodResult<T> b) {

		if (b.probability() >= a.probability()) {
			return b;
		}
		return a;

	}

	static class TermStatistics {

		HashMap<Target<?>, Integer> termInClassOccurencies = new HashMap<>();

		void update(Target<?> target) {

			termInClassOccurencies.compute(target, (k, v) -> {
				if (v == null) {
					return 1;
				}
				return ++v;
			});

			increaseTermTotalOccuriencies();
		}

		private void increaseTermTotalOccuriencies() {
			
			termTotalOccuriencies++;
		}
		
		int termInClassOccurencies(Target<?> target) {
			var result = termInClassOccurencies.get(target);
			return result;
		}

		int termTotalOccuriencies() {
			return this.termTotalOccuriencies;
		}

		private int termTotalOccuriencies;
	}

	TermStatistics getOrCreateTermsStatistics(Term term) {

		var result = this.termsStatistics.get(term);

		if (result==null)
			result = new TermStatistics();

		return result;
	}

	Function<TermsVector, Double> denominator = tv -> {
		var result = tv.terms().stream().map(x -> getOrCreateTermsStatistics(x))
				.map(x -> (x.termTotalOccuriencies() * 1.d) / totalFacts()).reduce(1.d, (x, y) -> x * y);

		return result;
	};

	Function<Target<?>, Double> py = target -> classes.get(target) * 1.d / totalFacts();

	Function<Target<?>, Function<Term, Double>> pxc = target -> term -> pxc(target, term);

	private double pxc(Target<?> target, Term term) {
		var termStatistic = getOrCreateTermsStatistics(term);
		// calculate p(x|c)
		var termInClassOccurencies = termStatistic.termInClassOccurencies(target);
		var pxc = (termInClassOccurencies * 1.d) / termStatistic.termTotalOccuriencies();
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
