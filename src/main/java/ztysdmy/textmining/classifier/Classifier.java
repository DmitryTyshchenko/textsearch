package ztysdmy.textmining.classifier;

import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.LikelihoodResult;

public interface Classifier<T> {
	
	LikelihoodResult<T> likelihood(Fact<T> fact);

}
