package ztysdmy.textmining.classifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.BiFunction;

import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.LikelihoodResult;
import ztysdmy.textmining.model.Target;
import ztysdmy.textmining.model.TermsVector;
import ztysdmy.textmining.model.TermsVectorBuilder;
import ztysdmy.textmining.repository.FactsRepository;

public class KNeighborEstimatorImpl<T> implements Classifier<T> {

	private final BiFunction<TermsVector, TermsVector, Double> estimationFunction;
	private FactsRepository<T> factsRespository;

	int complexity;

	public KNeighborEstimatorImpl(FactsRepository<T> factsRespository,
			BiFunction<TermsVector, TermsVector, Double> estimationFunction, int complexity) {
		this.estimationFunction = estimationFunction;
		this.complexity = complexity;
		this.factsRespository = factsRespository;
	}

	@Override
	public LikelihoodResult<T> likelihood(Fact<T> input) {

		var tempResults = collectDistances(TermsVectorBuilder.build(input, this.complexity));
		Collections.sort(tempResults, this.resultComparator);

		Target<T> target = tempResults.get(0).fact.target()
				.orElseThrow(() -> new RuntimeException("Fact in Storage can't be without Target"));

		LikelihoodResult<T> result = new LikelihoodResult<T>(target, 1.d);
		return result;
	}

	private ArrayList<FactPlusWeight<T>> collectDistances(TermsVector toEvalTermsVector) {

		ArrayList<FactPlusWeight<T>> tempResults = new ArrayList<>();
		var iterator = this.factsRespository.iterator();
		while (iterator.hasNext()) {

			var fact = iterator.next();
			var termsVector = TermsVectorBuilder.build(fact, this.complexity);

			Double weight = termsVector.eval(toEvalTermsVector, this.estimationFunction);
			tempResults.add(new FactPlusWeight<>(fact, weight));

		}
		return tempResults;
	}

	static class FactPlusWeight<T> {

		public final Fact<T> fact;
		public final Double weight;

		public FactPlusWeight(Fact<T> fact, Double weight) {

			this.fact = fact;
			this.weight = weight;
		}
	}

	private final Comparator<FactPlusWeight<T>> resultComparator = (a, b) -> a.weight < b.weight ? 1
			: a.weight == b.weight ? 0 : -1;

}
