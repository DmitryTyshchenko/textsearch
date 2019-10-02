package ztysdmy.textmining.learning;

import ztysdmy.textmining.classifier.LogisticRegression;
import ztysdmy.textmining.model.Binomial;
import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.PredictionResult;
import ztysdmy.textmining.model.Target;
import ztysdmy.textmining.repository.FactsRepository;

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
		// TODO Auto-generated method stub
		return null;
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

		public double getAlpha() {
			return alpha;
		}

		public void setAlpha(double alpha) {
			this.alpha = alpha;
		}

		private int epoches = 10000;
		private double alpha = 0.001d;

	}
}
