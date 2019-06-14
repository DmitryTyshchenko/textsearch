package ztysdmy.textmining.repository.query;

import java.util.Collection;

import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.repository.FactRepository;

public class GetAllFactsQuery implements Query {

	@Override
	public Collection<Fact> query() {
		
		return FactRepository.instance().get();
	}
	
}
