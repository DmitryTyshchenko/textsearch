package ztysdmy.textmining.repository.query;

import java.util.Collection;

import ztysdmy.textmining.model.Fact;

public interface Query<T> {

	Collection<Fact<T>> query();
}
