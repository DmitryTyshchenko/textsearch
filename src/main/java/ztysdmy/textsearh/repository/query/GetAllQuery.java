package ztysdmy.textsearh.repository.query;

import java.util.Collection;

import ztysdmy.textsearch.model.Document;
import ztysdmy.textsearch.repository.TextRepository;

public class GetAllQuery implements Query {

	@Override
	public Collection<Document> query() {
		
		return TextRepository.inMemoryInstance().get();
	}

	
}
