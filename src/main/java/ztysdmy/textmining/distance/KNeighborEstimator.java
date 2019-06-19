package ztysdmy.textmining.distance;

import java.util.List;

import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.LikelihoodResult;

public interface KNeighborEstimator<T> {
	
	List<LikelihoodResult<T>> likelihood(Fact<T> fact);

}
