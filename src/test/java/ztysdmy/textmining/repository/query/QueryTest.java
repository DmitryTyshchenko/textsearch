package ztysdmy.textmining.repository.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.Target;
import ztysdmy.textmining.repository.FactsRepository;
import ztysdmy.textmining.repository.InMemoryFactsRepository;
import ztysdmy.textmining.repository.query.FilterQueryDecorator;
import ztysdmy.textmining.repository.query.GetAllFactsQuery;

public class QueryTest {

	@Test
	public void shouldSiftAllResults() throws Exception {

		FactsRepository<Double> factRepository = new InMemoryFactsRepository<>();
		factRepository.populate(facts());
		var query = new FilterQueryDecorator<Double>(new GetAllFactsQuery<Double>(factRepository), List.of(document -> false));
		var queryResult = query.query();
		Assert.assertEquals(true, queryResult.isEmpty());
	}
	
	
	private Fact<Double> fact() {

		Fact<Double> document = new Fact<>("", new Target<Double>(0.1d));
		return document;
	}

	private Collection<Fact<Double>> facts() {
		Collection<Fact<Double>> result = new ArrayList<>();
		result.add(fact());
		return result;
	}
}
