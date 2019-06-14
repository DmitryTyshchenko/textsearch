package ztysdmy.textsearh.repository.query;

import java.util.Collection;

import ztysdmy.textsearch.model.Fact;
import ztysdmy.textsearch.repository.FactRepository;

public class GetAllFactsQuery implements Query {

	@Override
	public Collection<Fact> query() {
		
		return FactRepository.instance().get();
	}
	
}
