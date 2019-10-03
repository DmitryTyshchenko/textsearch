package ztysdmy.textmining.learning;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textmining.classifier.LogisticRegression;
import ztysdmy.textmining.model.Binomial;
import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.PredictionResult;
import ztysdmy.textmining.model.Target;
import ztysdmy.textmining.model.Term;
import ztysdmy.textmining.repository.FactsRepository;
import ztysdmy.textmining.repository.InMemoryFactsRepository;

import static ztysdmy.textmining.learning.LogisticRegressionLearningMachine.*;

public class LogisticRegressionLearningMachineTest {

	@Test
	public void shouldCalculateError() throws Exception {
		var target = new Target<Binomial>(Binomial.YES);
		var fact = new Fact<Binomial>("abc", target);
		var predictionResult = new PredictionResult<Binomial>(target, 0.7d);
		Assert.assertEquals(0.3, round(error(fact, predictionResult)), 0.d);
	}

	private double round(double value) {
		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(3, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	@Test
	public void shouldCollectMonomials() throws Exception {

		var learningMachine = new LogisticRegressionLearningMachine(factsRepository());
		LogisticRegression logisticRegression = learningMachine.createLogisticRegression();
		learningMachine.collectMonomials(logisticRegression);

		var monomial1 = logisticRegression.monomial(new Term("a"));
		// means that Monomial with Term 'a' exists
		Assert.assertTrue(monomial1.weight() != 0.d);

		var monomial2 = logisticRegression.monomial(new Term("notexists"));
		// means that Monomial with Term 'notexists' exists
		Assert.assertTrue(monomial2.weight() == 0.d);

	}

	FactsRepository<Binomial> factsRepository() {

		FactsRepository<Binomial> result = new InMemoryFactsRepository<>();
		result.add(facts());
		return result;
	}

	Collection<Fact<Binomial>> facts() {

		var result = new ArrayList<Fact<Binomial>>();

		var trueFact = fact("a", Binomial.YES);
		result.add(trueFact);

		var falseFact = fact("b", Binomial.NO);
		result.add(falseFact);

		return result;
	}

	private Fact<Binomial> fact(String value, Binomial targetValue) {
		var fact = new Fact<Binomial>(value, new Target<Binomial>(targetValue));

		return fact;
	}
}
