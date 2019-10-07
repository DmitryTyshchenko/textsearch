package ztysdmy.textmining.learning;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import ztysdmy.textmining.classifier.LogisticRegression;
import ztysdmy.textmining.classifier.LogisticRegression.Monomial;
import ztysdmy.textmining.model.Binomial;
import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.PredictionResult;
import ztysdmy.textmining.model.Target;
import ztysdmy.textmining.model.Term;
import ztysdmy.textmining.model.TermsVector;
import ztysdmy.textmining.model.TermsVectorBuilder;
import ztysdmy.textmining.repository.FactsRepository;

/**
 * http://rasbt.github.io/mlxtend/user_guide/classifier/LogisticRegression/
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

	BiConsumer<Fact<Binomial>, LogisticRegression> monomialsFromFact = (fact, logisticRegression) -> {
		var terms = TermsVectorBuilder.build(fact, 1);
		collectMonomials(logisticRegression, terms);
	};

	void collectMonomials(LogisticRegression logisticRegression) {

		withEachFact(monomialsFromFact, logisticRegression);
	}

	void learn(LogisticRegression logisticRegression) {
		whileStopCriteriaHasNotReached(() -> withEachFact(learnFromFact, logisticRegression));
	}
	
	private void whileStopCriteriaHasNotReached(LearningAction action) {

		for (int i = 0; i < params.getEpoches(); i++) {

			action.run();
		}
	}

	CalculateError calculateError = (fact, logisticRegression) -> error(fact,
			logisticRegression.predict(fact));

	UpdateRegressionWeights updateRegressionWeights = error -> {

		return (fact, logisticRegression) -> {
			updateMonomialWeight(logisticRegression.identity(), error);
			var terms = TermsVectorBuilder.build(fact, 0);
			terms.terms().forEach(term -> {
				updateMonomialWeight(term, error, logisticRegression);
			});
		};
	};

	LearnFromFact learnFromFact = (fact, logisticRegression) -> {
		calculateError.andThen(updateRegressionWeights).apply(fact, logisticRegression).accept(fact,
				logisticRegression);
	};

	private void updateMonomialWeight(Monomial monomial, double error) {
		var v = error * params.getLearningRate();
		var newWeight = monomial.weight() + v;
		monomial.updateWeight(newWeight);
	}

	private void updateMonomialWeight(Term term, double error, LogisticRegression logisticRegression) {
		var monomial = logisticRegression.monomial(term);
		updateMonomialWeight(monomial, error);
	}

	void withEachFact(BiConsumer<Fact<Binomial>, LogisticRegression> consumer, LogisticRegression logisticRegression) {

		factsRepository.stream().forEach(fact -> {
			consumer.accept(fact, logisticRegression);
		});
	}

	private void collectMonomials(LogisticRegression logisticRegression, TermsVector terms) {

		terms.terms().forEach(term -> logisticRegression.putTermToPolynomIfAbsent(term));
	}

	@Override
	public LogisticRegression build() {
		var logisticRegression = createLogisticRegression();
		collectMonomials(logisticRegression);
		learn(logisticRegression);
		return logisticRegression;
	}

	LogisticRegression createLogisticRegression() {
		var result = new LogisticRegression();
		return result;
	}

	static double error(Fact<Binomial> fact, PredictionResult<Binomial> prediction) {
		var target = target(fact);
		var result = (target.value().value() - prediction.probability());
		return result;
	}

	static Target<Binomial> target(Fact<Binomial> fact) {
		return fact.target().orElseThrow(() -> new RuntimeException("Fact without target"));
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

	}
	
	@FunctionalInterface
	static interface LearningAction extends Runnable {

	}

	@FunctionalInterface
	static interface CalculateError extends BiFunction<Fact<Binomial>, LogisticRegression, Double> {
		
	}
	
	@FunctionalInterface
	static interface UpdateRegressionWeights extends Function<Double, BiConsumer<Fact<Binomial>, LogisticRegression>> {
		
	}
	
	@FunctionalInterface
	static interface LearnFromFact extends BiConsumer<Fact<Binomial>, LogisticRegression> {
		
	}
}
