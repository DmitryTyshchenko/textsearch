package ztysdmy.textmining.repository;

import java.util.Collection;
import java.util.Iterator;

import ztysdmy.textmining.model.Fact;

public interface FactsRepository<T> {

	Iterator<Fact<T>> iterator();

	void add(Collection<Fact<T>> facts);

	void clear();
	
	int size();
}
