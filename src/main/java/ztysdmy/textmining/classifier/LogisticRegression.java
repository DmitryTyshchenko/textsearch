package ztysdmy.textmining.classifier;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

import ztysdmy.textmining.functions.Sigmoid;
import ztysdmy.textmining.model.Binomial;
import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.PredictionResult;
import ztysdmy.textmining.model.Target;
import ztysdmy.textmining.model.Term;
import ztysdmy.textmining.model.TermsVector;
import ztysdmy.textmining.model.TermsVectorBuilder;
import ztysdmy.textmining.pmml.DataDictionary;
import ztysdmy.textmining.pmml.PMMLExportable;
import ztysdmy.textmining.pmml.RegressionModel;


public class LogisticRegression extends AbstractClassifier<Binomial> implements PMMLExportable {

	public LogisticRegression(int complexity) {
		super(complexity);
	}

	public LogisticRegression() {
		super(0);
	}
	
	Monomial IDENTITY = initialMonomialWeight();

	HashMap<Term, Monomial> POLYNOMIAL = new HashMap<>();
	
	@Override
	public LogisticRegressionPredictionResult predict(Fact<Binomial> fact) {
		var result = monomialsFromFact.andThen(sumOfMonomials.andThen(applySigmoid)).apply(fact);
		return new LogisticRegressionPredictionResult(result);
	}

	Function<Fact<Binomial>, TermsVector> monomialsFromFact = fact->TermsVectorBuilder.build(fact, this.getComplexity());
	
	public void addTermToPolynomIfAbsent(Term term) {
		this.POLYNOMIAL.putIfAbsent(term, initialMonomialWeight());
	}
	
	public Monomial identity() {
		return IDENTITY;
	}
	
	Monomial initialMonomialWeight() {
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
		return Optional.ofNullable(this.POLYNOMIAL.get(term)).orElseGet(()->new Monomial());
	}

	public static class Monomial {

		double weight = 0.d;

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
	
	@Override
	public String toString() {

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(this.IDENTITY.weight());

		this.POLYNOMIAL.forEach((term, monomial) -> {
			stringBuilder.append("+");
			stringBuilder.append(Double.toString(monomial.weight()));
			stringBuilder.append("*");
			stringBuilder.append(term.value());
		});

		return stringBuilder.toString();
	}

	@Override
	public DataDictionary dataDictionary() {
		return LogisticRegressionToPMMLUtility.dataDictionary(this);
	}

	@Override
	public RegressionModel regressionModel(String modelName) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
