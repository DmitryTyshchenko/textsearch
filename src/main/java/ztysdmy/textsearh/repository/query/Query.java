package ztysdmy.textsearh.repository.query;

import java.util.Collection;

import ztysdmy.textsearch.model.Document;

public interface Query {

	Collection<Document> query();
}
