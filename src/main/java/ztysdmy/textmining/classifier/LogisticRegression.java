package ztysdmy.textmining.classifier;

import java.util.HashMap;
import java.util.function.Function;

import ztysdmy.textmining.functions.Sigmoid;
import ztysdmy.textmining.model.Binomial;
import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.PredictionResult;
import ztysdmy.textmining.model.Target;
import ztysdmy.textmining.model.Term;
import ztysdmy.textmining.model.TermsVector;
import ztysdmy.textmining.model.TermsVectorBuilder;

public class LogisticRegression implements Classifier<Binomial> {

	Monomial IDENTITY = monomialWithRandomWeight();

	HashMap<Term, Monomial> POLYNOMIAL = new HashMap<>();
	
	@Override
	public LogisticRegressionPredictionResult predict(Fact<Binomial> fact) {
		var terms = TermsVectorBuilder.build(fact, 1);
		var result = sumOfMonomials.andThen(applySigmoid).apply(terms);
		return new LogisticRegressionPredictionResult(result);
	}

	public void putTermToPolynomIfAbsent(Term term) {
		this.POLYNOMIAL.putIfAbsent(term, monomialWithRandomWeight());
	}
	
	Monomial monomialWithRandomWeight() {
		var result = new Monomial();
		result.updateWeight(Math.random());
		return result;
	}
	
	Function<TermsVector, Double> sumOfMonomials = t -> sumOfMonomials(t);
	
	private double sumOfMonomials(TermsVector terms) {
		var result = terms.terms().stream().map(t -> monomial(t)).mapToDouble(m -> m.weight).reduce(IDENTITY.weight,
				(a, b) -> a + b);
		return result;
	}

	Function<Double, Double> applySigmoid = d -> applySigmoid(d);

	private double applySigmoid(double d) {
		Sigmoid sigmoid = new Sigmoid();
		var result = sigmoid.apply(d);
		return result;
	}

	public Monomial monomial(Term term) {
		Monomial defaultMonomial = new Monomial();
		var monomial = this.POLYNOMIAL.getOrDefault(term, defaultMonomial);
		return monomial;
	}

	public static class Monomial {

		double weight;

		public void updateWeight(double weight) {
			this.weight = weight;
		}

		public double weight() {
			return this.weight;
		}
	}

	public static class LogisticRegressionPredictionResult extends PredictionResult<Binomial> {

		private static final Target<Binomial> target = new Target<Binomial>(Binomial.YES);
		
		public LogisticRegressionPredictionResult(Double probability) {
			super(target, probability);
		}
	}
	
}
