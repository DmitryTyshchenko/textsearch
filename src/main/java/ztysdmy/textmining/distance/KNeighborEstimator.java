package ztysdmy.textmining.distance;

import java.util.Collection;

import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.LikelihoodResult;

public interface KNeighborEstimator {
	
	Collection<LikelihoodResult> likelihood(Fact fact);

}
