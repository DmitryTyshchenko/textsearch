package ztysdmy.textmining.classifier;

import java.util.function.Function;

public class Sigmoid implements Function<Double, Double> {

	@Override
	public Double apply(Double t) {
		//http://timvieira.github.io/blog/post/2014/02/11/exp-normalize-trick/
		// stable sigmoid
		return 1/(1 + Math.exp(t>=0.d?negativeValue(t):t));
	}

	private double negativeValue(Double t) {
		return ~t.longValue()+1;
	}
}
