package ztysdmy.textsearch.model;

import java.util.function.BiFunction;

import ztysdmy.textsearch.distance.TanimotoDistance;

public class TextSegmentField extends Field<TextSegment> implements EvaluationFunctionProvider<TermsVector> {

	private BiFunction<TermsVector, TermsVector, Double> evalFunction;

	public TextSegmentField(String name, TextSegment value) {
		super(name, value);
		initEvalFunction(name);
	}

	@Override
	public BiFunction<TermsVector, TermsVector, Double> evalFunction() {

		return this.evalFunction;
	}

	private void initEvalFunction(String name) {

		this.evalFunction = new TanimotoDistance();
		// smooth if not title
		if (!"title".equals(name)) {

			this.evalFunction = evalFunction.andThen(r -> r*0.9d);
		}
	}
}
