package ztysdmy.textming;

import static ztysdmy.functional.tailcall.TailCallUtility.call;
import static ztysdmy.functional.tailcall.TailCallUtility.done;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import ztysdmy.functional.tailcall.TailCall;
import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.Target;

public class FactsUtility {

	private FactsUtility() {
	}

	static Function<Integer, Function<Integer, Target<String>>> chooseTarget() {
		return a -> b -> {
			var toEval = new IF<Target<String>>(() -> a.equals(b), () -> createTarget("classA"));
			return match(toEval, () -> createTarget("classB"));
		};
	}

	private static Function<Target<String>, Fact<String>> setTargetToFact = x -> {
		var fact = new Fact<String>("test", x);
		return fact;
	};

	public static Supplier<Collection<Fact<String>>> factsSupplier() {
		return () -> {

			ArrayList<Fact<String>> collector = new ArrayList<>();
			var result =  collect(0, collector).invoke();
			return result;
		};
	}

	private static TailCall<Collection<Fact<String>>> collect(int i, ArrayList<Fact<String>> collector) {
		collector.add(chooseTarget().apply(1).andThen(setTargetToFact).apply(i));
		if (i == 4) {
			return done(collector);
		}
		return call(() -> collect(i+1, collector));
	}

	private static Target<String> createTarget(String value) {
		return new Target<String>(value);
	}

	private static class IF<T> {

		Supplier<Boolean> predicate;
		Supplier<T> action;

		IF(Supplier<Boolean> predicate, Supplier<T> action) {
			this.predicate = predicate;
			this.action = action;
		}
	}

	private static <T> T match(IF<T> a, Supplier<T> defaultAction) {

		if (a.predicate.get().equals(Boolean.TRUE)) {
			return a.action.get();
		}

		return defaultAction.get();
	}

}
