package ztysdmy.textmining.learning;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textmining.classifier.Classifier;
import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.Target;
import ztysdmy.textmining.repository.FactsRepository;
import ztysdmy.textmining.repository.InMemoryFactsRepository;

public class NaiveBayesLearningMachineTest {

	@Test
	public void buildBayesLearningMachine() throws Exception {

		var factsRepository = new InMemoryFactsRepository<String>();
		initRepository(factsRepository);

		NaiveBayesLearningMachine<String> learningMachine = new NaiveBayesLearningMachine<>(factsRepository);

		Classifier<String> classifier = learningMachine.build();

		var result = classifier.likelihood(new Fact<String>("sdfsd sfsfew test"));

		Assert.assertEquals("classB", result.target().value());
		Assert.assertTrue(result.probability()<1.d);

	}

	private void initRepository(FactsRepository<String> factsRepository) {

		factsRepository.add(factsSupplier.get());
	}

	Function<String, Target<String>> toTarget = x -> new Target<String>(x);

	Function<Integer, Target<String>> chooseTarger = x -> {

		if (x < 1) {
			return toTarget.apply("classA");
		}
		return toTarget.apply("classB");
	};

	private Supplier<Collection<Fact<String>>> factsSupplier = () -> {

		ArrayList<Fact<String>> result = new ArrayList<>();

		for (int i = 0; i < 4; i++) {
			var target = chooseTarger.apply(i);
			var fact = new Fact<String>("test", target);
			result.add(fact);
		}

		return result;
	};

}
