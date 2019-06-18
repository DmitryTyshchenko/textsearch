package ztysdmy.textmining.repository;

import java.util.Collection;

import ztysdmy.textmining.model.Fact;

public interface FactRepository<T> {

	Collection<Fact<T>> get();

	void populate(Collection<Fact<T>> documents);

	void clear();
	
}
