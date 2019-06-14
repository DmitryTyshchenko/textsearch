package ztysdmy.textmining.repository;

import java.util.Collection;

import ztysdmy.textmining.model.Fact;

public interface FactRepository {

	Collection<Fact> get();

	void populate(Collection<Fact> documents);

	void clear();

	public static FactRepository instance() {
		return InMemoryFactRepository.instance();
	}
}
