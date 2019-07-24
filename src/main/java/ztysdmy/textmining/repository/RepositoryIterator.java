package ztysdmy.textmining.repository;

import java.util.Optional;

import ztysdmy.textmining.model.Fact;

public interface RepositoryIterator<T> {

	 Optional<Fact<T>> next();
}
