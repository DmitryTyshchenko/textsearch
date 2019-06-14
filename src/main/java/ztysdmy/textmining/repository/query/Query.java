package ztysdmy.textmining.repository.query;

import java.util.Collection;

import ztysdmy.textmining.model.Fact;

public interface Query {

	Collection<Fact> query();
}
