package ztysdmy.textmining.distance;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

import ztysdmy.textmining.model.TermsVector;

public class TanimotoDistance implements BiFunction<TermsVector, TermsVector, Double> {

	@Override
	public Double apply(TermsVector termsVector1, TermsVector termsVector2) {
		var x = termsVector1.terms().keySet().size();
		var y = termsVector2.terms().keySet().size();

		Set<String> localTermsVector1 = new HashSet<>();
		localTermsVector1.addAll(termsVector1.terms().keySet());
		Set<String> localTermsVector2 = new HashSet<>();
		localTermsVector2.addAll(termsVector2.terms().keySet());

		localTermsVector1.retainAll(localTermsVector2);

		var common = localTermsVector1.size();

		var nominator = x + y - 2.d * common;
		var denominator =  x + y - common;
		
		var distance =  nominator/denominator;
		//convert result to probability
		return 1.d - distance;
	}

}
