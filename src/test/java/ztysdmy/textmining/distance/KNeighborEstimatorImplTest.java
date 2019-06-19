package ztysdmy.textmining.distance;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.Target;
import ztysdmy.textmining.repository.FactRepository;
import ztysdmy.textmining.repository.InMemoryFactRepository;

public class KNeighborEstimatorImplTest {

	private Fact<String> fact(String value, String classLabel) {
		var fact = new Fact<>(value, new Target<>(classLabel));
		return fact;
	}

	@Test
	public void shouldCorrectlyClasify() throws Exception {

		FactRepository<String> factRepository = new InMemoryFactRepository<>();

		List<Fact<String>> facts = List.of(fact("some test text", "classA"),
				fact("should be correct text for classification", "classB"));
		factRepository.populate(facts);

		KNeighborEstimator<String> estimator = new KNeighborEstimatorImpl<>(factRepository, new TanimotoDistance(), 2);

		Fact<String> toEval = new Fact<>("correct text");

		var estimated = estimator.likelihood(toEval);

		String estimatedClass = estimated.get(0).fact().target().get().value();

		Assert.assertEquals("classB", estimatedClass);

	}
}
