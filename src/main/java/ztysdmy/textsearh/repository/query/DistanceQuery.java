package ztysdmy.textsearh.repository.query;

import java.util.Collection;

import ztysdmy.textsearch.model.Document;
import ztysdmy.textsearch.model.TermsVector;
import ztysdmy.textsearch.repository.TextRepository;

public class DistanceQuery implements Query {

	private TermsVector termsVector;
	
	protected TextRepository textRepository = TextRepository.instance(); 
	
	public DistanceQuery(TermsVector termsVector) {
		this.termsVector = termsVector;
	}
	
	@Override
	public Collection<Document> query() {
		 return textRepository.likelihood(this.termsVector);
	}

}
