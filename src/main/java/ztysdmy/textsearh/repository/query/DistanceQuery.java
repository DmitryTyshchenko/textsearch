package ztysdmy.textsearh.repository.query;

import java.util.Collection;

import ztysdmy.textsearch.model.Document;
import ztysdmy.textsearch.model.TermsVector;
import ztysdmy.textsearch.repository.TextRepository;

public class DistanceQuery implements Query {

	private TermsVector termsVector;
	
	public DistanceQuery(TermsVector termsVector) {
		this.termsVector = termsVector;
	}
	
	@Override
	public Collection<Document> query() {
		 return TextRepository.inMemoryInstance().get(this.termsVector);
	}

}
