package ztysdmy.textmining.functions;

import java.util.function.Function;

public class Sigmoid implements Function<Double, Double> {

	@Override
	public Double apply(Double t) {
		//return 1/(1 + Math.exp(t>=0.d?negativeValue(t):t));
		return 1/(1 + Math.exp(negativeValue(t)));
	}

	private double negativeValue(Double t) {
		return ~t.longValue()+1;
	}
}
