package ztysdmy.textmining.classifier;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.Target;
import ztysdmy.textmining.repository.InMemoryFactsRepository;

import ztysdmy.textmining.classifier.KNeighborEstimatorImpl.KNeighborEstimatorImplBuilder;
import ztysdmy.textmining.functions.TanimotoDistance;

public class KNeighborEstimatorImplTest {

	private Fact<String> fact(String value, String classLabel) {
		var fact = new Fact<>(value, new Target<>(classLabel));
		return fact;
	}

	@Test
	public void shouldCorrectlyClasify() throws Exception {

		var factRepository = new InMemoryFactsRepository<String>();

		var facts = List.of(fact("some test text", "classA"),
				fact("should be correct text for classification", "classB"));
		factRepository.add(facts);
		
		var estimator = new KNeighborEstimatorImplBuilder<String>(factRepository, new TanimotoDistance())
				.with(builder -> builder.complexity = 0).build();

		var toEval = new Fact<String>("correct text");

		var estimated = estimator.predict(toEval);

		var estimatedClass = estimated.target().value();

		Assert.assertEquals("classB", estimatedClass);

	}
}
