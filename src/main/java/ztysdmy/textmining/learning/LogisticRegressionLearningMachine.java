package ztysdmy.textmining.learning;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import ztysdmy.textmining.classifier.LogisticRegression;
import ztysdmy.textmining.model.Binomial;
import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.Term;
import ztysdmy.textmining.model.TermsVector;
import ztysdmy.textmining.model.TermsVectorBuilder;
import ztysdmy.textmining.repository.FactsRepository;

/**
 * http://rasbt.github.io/mlxtend/user_guide/classifier/LogisticRegression/
 * 
 * @author dmytro.tyshchenko
 *
 */

public class LogisticRegressionLearningMachine implements Supervized<Binomial> {

	private FactsRepository<Binomial> factsRepository;

	private LogisticRegressionParams params = new LogisticRegressionParams();

	public LogisticRegressionLearningMachine(FactsRepository<Binomial> factsRepository) {
		this.factsRepository = factsRepository;
	}

	public LogisticRegressionLearningMachine(FactsRepository<Binomial> factsRepository,
			LogisticRegressionParams params) {
		this(factsRepository);
		this.params = params;
	}

	@Override
	public LogisticRegression build() {
		var logisticRegression = createLogisticRegression();
		collectMonomials(logisticRegression);
		learn(logisticRegression);
		return logisticRegression;
	}
	
	Function<Fact<Binomial>, TermsVector> generateTermsFromFact = fact->TermsVectorBuilder.build(fact, this.params.getComplexity());
	
	BiConsumer<Fact<Binomial>, LogisticRegression> monomialsFromFact = (fact, logisticRegression) -> {
		var terms = generateTermsFromFact.apply(fact);
		collectMonomials(logisticRegression, terms);
	};

	void collectMonomials(LogisticRegression logisticRegression) {

		withEachFact(monomialsFromFact, logisticRegression);
	}

	void learn(LogisticRegression logisticRegression) {
		whileStopCriteriaHasNotReached(() -> withEachFact(learnFromFact, logisticRegression));
	}

	private void whileStopCriteriaHasNotReached(LearnFromFacts action) {

		for (int i = 0; i < params.getEpoches(); i++) {

			action.run();
		}
	}

	ErrorCalculator calculateError = (fact, logisticRegression) -> this.params.getCostFunction().error(fact,
			logisticRegression.predict(fact));

	RegressionWeightsUpdater updateRegressionWeights = error -> {

		return (fact, logisticRegression) -> {
			params.getCostFunction().updateWeight(logisticRegression.identity(), error, params.getLearningRate());
			var terms = generateTermsFromFact.apply(fact);
			terms.terms().forEach(term -> {
				updateWeight(term, error, logisticRegression);
			});
		};
	};

	LearnFromFact learnFromFact = (fact, logisticRegression) -> {
		calculateError.andThen(updateRegressionWeights).apply(fact, logisticRegression).accept(fact,
				logisticRegression);
	};

	private void updateWeight(Term term, double error, LogisticRegression logisticRegression) {
		var monomial = logisticRegression.monomial(term);
		params.getCostFunction().updateWeight(monomial, error, params.getLearningRate());
	}

	void withEachFact(BiConsumer<Fact<Binomial>, LogisticRegression> consumer, LogisticRegression logisticRegression) {

		factsRepository.stream().forEach(fact -> {
			consumer.accept(fact, logisticRegression);
		});
	}

	private void collectMonomials(LogisticRegression logisticRegression, TermsVector terms) {

		terms.terms().forEach(term -> logisticRegression.addTermToPolynomIfAbsent(term));
	}

	LogisticRegression createLogisticRegression() {
		var result = new LogisticRegression();
		return result;
	}

	public static class LogisticRegressionParams {

		public int getEpoches() {
			return epoches;
		}

		public void setEpoches(int epoches) {
			this.epoches = epoches;
		}

		public double getLearningRate() {
			return learningRate;
		}

		public void setLearningRate(double alpha) {
			this.learningRate = alpha;
		}

		private int epoches = 10000;
		private double learningRate = 0.001d;

		private CostFunction costFunction = new LogLikelihoodCostFunction();

		public CostFunction getCostFunction() {
			return costFunction;
		}

		public void setCostFunction(CostFunction costFunction) {
			this.costFunction = costFunction;
		}
		
		private int complexity = 0;
		
		public void setComplexity(int complexity) {
			this.complexity = complexity;
		}
		
		public int getComplexity() {
			return this.complexity;
		}

	}

	@FunctionalInterface
	static interface LearnFromFacts extends Runnable {

	}

	@FunctionalInterface
	static interface ErrorCalculator extends BiFunction<Fact<Binomial>, LogisticRegression, Double> {

	}

	@FunctionalInterface
	static interface RegressionWeightsUpdater extends Function<Double, BiConsumer<Fact<Binomial>, LogisticRegression>> {

	}

	@FunctionalInterface
	static interface LearnFromFact extends BiConsumer<Fact<Binomial>, LogisticRegression> {

	}
}
