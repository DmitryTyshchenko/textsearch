package ztysdmy.textmining.classifier;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.Target;

public class LogisticRegressionTest {

	@Test
	public void shouldProcessEmpty() throws Exception {
		Target<String> target = new Target<String>("YES");
		LogisticRegression<String> logisticRegression = new LogisticRegression<>(target);
		Fact<String> fact = new Fact<>("test");
		var result = logisticRegression.likelihood(fact);
		Assert.assertEquals(0.5d, result.probability(), 0.d);
	}
}
