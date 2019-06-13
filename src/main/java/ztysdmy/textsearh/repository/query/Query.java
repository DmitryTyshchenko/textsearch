package ztysdmy.textsearh.repository.query;

import java.util.Collection;

import ztysdmy.textsearch.model.Fact;

public interface Query {

	Collection<Fact> query();
}
