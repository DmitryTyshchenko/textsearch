package ztysdmy.textming;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.Target;

public class FactsUtility {

	private FactsUtility() {}
		
	static Function<Integer, Function<Integer, Target<String>>> chooseTarget() {
		return a-> b-> {
			var toEval = new IF<Target<String>>(()->a.equals(b), ()->createTarget("classA"));
			return match(toEval, ()->createTarget("classB"));
		};
		
	}
	
	public static Supplier<Collection<Fact<String>>> factsSupplier() {
		return ()->{
			
			Integer THRESHOLD = Integer.valueOf(1);
			
			Function<Target<String>, Fact<String>> setTargetToFact = x->{
				var fact = new Fact<String>("test", x);
				return fact;
			};
			
			ArrayList<Fact<String>> collector = new ArrayList<>();
					
			for (int i=0;i<4;i++) {
				collector.add(chooseTarget().apply(THRESHOLD).andThen(setTargetToFact).apply(i));
			}
			
			return collector;
		};
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
