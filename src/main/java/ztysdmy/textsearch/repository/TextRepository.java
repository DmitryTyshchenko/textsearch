package ztysdmy.textsearch.repository;

import java.util.Collection;

import ztysdmy.textsearch.model.Document;
import ztysdmy.textsearch.model.TermsVector;

public interface TextRepository {

	Collection<Document> get(TermsVector termsVector);
	Collection<Document> get();
	void populate(Collection<Document> documents);
	void clear();
	
}
