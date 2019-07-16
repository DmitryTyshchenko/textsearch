package ztysdmy.textmining.repository;

import java.util.Collection;

import ztysdmy.textmining.model.Fact;

public interface LearningFactsRepository<T> {
	
	Collection<Fact<T>> learningSet();
	
	Collection<Fact<T>> testSet();

}
