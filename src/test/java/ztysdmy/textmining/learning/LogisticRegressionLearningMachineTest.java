package ztysdmy.textmining.learning;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textmining.model.Binomial;
import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.PredictionResult;
import ztysdmy.textmining.model.Target;
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
	
	private Fact<Binomial> fact(String value, Binomial targetValue) {
		var fact = new Fact<Binomial>(value, new Target<Binomial>(targetValue));

		return fact;
	}
}
