package ztysdmy.textmining.classifier;

import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.PredictionResult;

public interface Classifier<T> {
	
	PredictionResult<T> predict(Fact<T> fact);

}
