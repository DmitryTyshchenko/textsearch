package ztysdmy.textsearch.repository;

import java.util.Collection;

import ztysdmy.textsearch.model.Document;
import ztysdmy.textsearch.model.TermsVector;

public interface TextRepository {

	Collection<Document> likelihood(TermsVector termsVector);

	Collection<Document> get();

	void populate(Collection<Document> documents);

	void clear();

	public static TextRepository instance() {
		return InMemoryTextRepository.instance();
	}
}
