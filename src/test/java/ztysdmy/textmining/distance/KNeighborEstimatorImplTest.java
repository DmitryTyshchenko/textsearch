package ztysdmy.textmining.distance;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textmining.classifier.Classifier;
import ztysdmy.textmining.classifier.KNeighborEstimatorImpl;
import ztysdmy.textmining.classifier.TanimotoDistance;
import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.Target;
import ztysdmy.textmining.repository.FactsRepository;
import ztysdmy.textmining.repository.InMemoryFactsRepository;

public class KNeighborEstimatorImplTest {

	private Fact<String> fact(String value, String classLabel) {
		var fact = new Fact<>(value, new Target<>(classLabel));
		return fact;
	}

	@Test
	public void shouldCorrectlyClasify() throws Exception {

		FactsRepository<String> factRepository = new InMemoryFactsRepository<>();

		List<Fact<String>> facts = List.of(fact("some test text", "classA"),
				fact("should be correct text for classification", "classB"));
		factRepository.add(facts);

		Classifier<String> estimator = new KNeighborEstimatorImpl<>(factRepository, new TanimotoDistance(), 1);

		Fact<String> toEval = new Fact<>("correct text");

		var estimated = estimator.likelihood(toEval);

		String estimatedClass = estimated.get(0).fact().target().get().value();

		Assert.assertEquals("classB", estimatedClass);

	}
}
