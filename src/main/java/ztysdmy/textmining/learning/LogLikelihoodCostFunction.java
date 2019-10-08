package ztysdmy.textmining.learning;

import ztysdmy.textmining.classifier.LogisticRegression.Monomial;
import ztysdmy.textmining.model.Binomial;
import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.PredictionResult;
import ztysdmy.textmining.model.Target;

public class LogLikelihoodCostFunction implements CostFunction {

	@Override
	public double error(Fact<Binomial> fact, PredictionResult<Binomial> prediction) {
		var target = target(fact);
		var result = (target.value().value() - prediction.probability());
		return result;
	}

	Target<Binomial> target(Fact<Binomial> fact) {
		return fact.target().orElseThrow(() -> new RuntimeException("Fact without target"));
	}
	
	//gradient ascent
	@Override
	public void updateWeight(Monomial monomial, double error, double learningRate) {
		var v = error * learningRate;
		var newWeight = monomial.weight() + v;
		monomial.updateWeight(newWeight);
	}

}
