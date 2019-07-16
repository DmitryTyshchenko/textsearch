package ztysdmy.textmining.repository.query;

import java.util.Collection;

import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.repository.FactsRepository;

public class GetAllFactsQuery<T> implements Query<T> {

	private FactsRepository<T> factRepository;

	public GetAllFactsQuery(FactsRepository<T> factRepository) {
		this.factRepository = factRepository;
	}

	@Override
	public Collection<Fact<T>> query() {

		return factRepository.getAll();
	}

}
