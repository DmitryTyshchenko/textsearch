package ztysdmy.textmining.classifier;

import java.util.List;

import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.LikelihoodResult;

public interface Classifier<T> {
	
	List<LikelihoodResult<T>> likelihood(Fact<T> fact);

}
