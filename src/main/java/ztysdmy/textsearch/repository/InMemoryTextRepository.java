package ztysdmy.textsearch.repository;

import java.util.Collection;
import java.util.SortedSet;

import ztysdmy.textsearch.model.Document;
import ztysdmy.textsearch.model.TermsVector;

public class InMemoryTextRepository implements TextRepository {

	private InMemoryTextRepository() {
	};

	private static InMemoryTextRepository instance;

	public synchronized static InMemoryTextRepository instance() {

		if (instance == null) {

			instance = new InMemoryTextRepository();
		}

		return instance;
	}

	@Override
	public SortedSet<Document> get(TermsVector termsVector) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Document> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void populate(Collection<Document> document) {
		// TODO Auto-generated method stub
		
	}

}
