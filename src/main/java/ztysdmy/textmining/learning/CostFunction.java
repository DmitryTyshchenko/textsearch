package ztysdmy.textmining.learning;

import ztysdmy.textmining.classifier.LogisticRegression.Monomial;
import ztysdmy.textmining.model.Binomial;
import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.PredictionResult;

public interface CostFunction {

	double error(Fact<Binomial> fact, PredictionResult<Binomial> prediction);
	void updateWeight(Monomial monomial, double error, double learningRate);
}
