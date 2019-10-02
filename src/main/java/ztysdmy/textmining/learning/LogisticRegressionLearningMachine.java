package ztysdmy.textmining.learning;

import ztysdmy.textmining.classifier.LogisticRegression;
import ztysdmy.textmining.model.Binomial;
import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.PredictionResult;
import ztysdmy.textmining.model.Target;

public class LogisticRegressionLearningMachine implements Supervized<Binomial> {

	@Override
	public LogisticRegression build() {
		// TODO Auto-generated method stub
		return null;
	}

	double error(Fact<Binomial> fact, PredictionResult<Binomial> prediction) {
		var target = target(fact);
		var result = (target.value().value() - prediction.probability());
		return result;
	}

	Target<Binomial> target(Fact<Binomial> fact) {
		return fact.target().orElseThrow(() -> new RuntimeException("Fact without target"));
	}
}
