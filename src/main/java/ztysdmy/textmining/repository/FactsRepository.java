package ztysdmy.textmining.repository;

import java.util.Collection;

import ztysdmy.textmining.model.Fact;

public interface FactsRepository<T> {

	Collection<Fact<T>> getAll();

	void populate(Collection<Fact<T>> facts);

	void clear();
	
	int size();
}
