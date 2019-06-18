package ztysdmy.textmining.distance;

import java.util.Collection;

import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.LikelihoodResult;

public interface KNeighborEstimator<T> {
	
	Collection<LikelihoodResult<T>> likelihood(Fact<T> fact);

}
