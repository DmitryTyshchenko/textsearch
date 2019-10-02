package ztysdmy.textmining.learning;

import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

import static ztysdmy.textming.FactsUtility.factsSupplier;
import ztysdmy.textmining.classifier.Classifier;
import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.Target;
import ztysdmy.textmining.repository.FactsRepository;
import ztysdmy.textmining.repository.InMemoryFactsRepository;
//import static ztysdmy.textmining.FactsUtility.*;

public class NaiveBayesLearningMachineTest {

	@Test
	public void buildBayesLearningMachine() throws Exception {

		var factsRepository = new InMemoryFactsRepository<String>();
		initRepository(factsRepository);

		NaiveBayesLearningMachine<String> learningMachine = new NaiveBayesLearningMachine<>(factsRepository);

		Classifier<String> classifier = learningMachine.build();

		var result = classifier.predict(new Fact<String>("sdfsd sfsfew test"));

		Assert.assertEquals("classB", result.target().value());
		Assert.assertTrue(result.probability()<1.d);

	}

	private void initRepository(FactsRepository<String> factsRepository) {
		
		factsRepository.add(factsSupplier().get());
	}

	Function<String, Target<String>> toTarget = x -> new Target<String>(x);

	Function<Integer, Target<String>> chooseTarger = x -> {

		if (x < 1) {
			return toTarget.apply("classA");
		}
		return toTarget.apply("classB");
	};

}
