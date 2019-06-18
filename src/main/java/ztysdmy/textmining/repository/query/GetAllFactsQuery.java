package ztysdmy.textmining.repository.query;

import java.util.Collection;

import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.repository.FactRepository;

public class GetAllFactsQuery<T> implements Query<T> {

	private FactRepository<T> factRepository;

	public GetAllFactsQuery(FactRepository<T> factRepository) {
		this.factRepository = factRepository;
	}

	@Override
	public Collection<Fact<T>> query() {

		return factRepository.get();
	}

}
