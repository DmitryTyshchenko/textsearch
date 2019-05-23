package ztysdmy.textsearch.repository;

import java.util.Collection;
import java.util.SortedSet;

import ztysdmy.textsearch.model.Document;
import ztysdmy.textsearch.model.TermsVector;

public interface TextRepository {

	SortedSet<Document> get(TermsVector termsVector);
	Collection<Document> getAll();
	void add(Document document);
	
}
