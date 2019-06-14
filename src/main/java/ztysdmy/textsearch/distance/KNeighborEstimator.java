package ztysdmy.textsearch.distance;

import java.util.Collection;

import ztysdmy.textsearch.model.Fact;
import ztysdmy.textsearch.model.LikelihoodResult;

public interface KNeighborEstimator {
	
	Collection<LikelihoodResult> likelihood(Fact fact);

}
