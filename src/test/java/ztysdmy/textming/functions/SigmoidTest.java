package ztysdmy.textming.functions;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textmining.functions.Sigmoid;

public class SigmoidTest {

	@Test
	public void sigmoidTestAt0() throws Exception {
		var value = 0.d;
		var sigmoid = new Sigmoid();
		Assert.assertEquals(0.5d, sigmoid.apply(value), 0.d);
	}

}
