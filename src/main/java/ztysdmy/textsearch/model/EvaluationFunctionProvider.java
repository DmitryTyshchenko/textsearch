package ztysdmy.textsearch.model;

import java.util.function.BiFunction;

public interface EvaluationFunctionProvider<T> {

	BiFunction<T, T, Double> evalFunction();
	
}
