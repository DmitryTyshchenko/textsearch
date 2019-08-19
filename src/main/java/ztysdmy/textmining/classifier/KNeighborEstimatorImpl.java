package ztysdmy.textmining.classifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Consumer;

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

	int neighborns = 1;

	public KNeighborEstimatorImpl(FactsRepository<T> factsRespository,
			BiFunction<TermsVector, TermsVector, Double> estimationFunction) {
		this.estimationFunction = estimationFunction;
		this.factsRespository = factsRespository;
	}

	@Override
	public LikelihoodResult<T> likelihood(Fact<T> input) {
		var tempResults = collectDistances(TermsVectorBuilder.build(input, this.complexity));
		Collections.sort(tempResults, this.resultComparator);
		return likelihood2(tempResults);
	}

	private LikelihoodResult<T> likelihood2(ArrayList<FactPlusWeight<T>> estimationsResult) {

		HashMap<Target<T>, Integer> innerMap = new HashMap<Target<T>, Integer>();
		Target<T> clazz = null;
		var occurrences = 0;

		var counter = 0;

		while (counter < neighborns && counter < estimationsResult.size()) {

			var target = estimationsResult.get(counter).fact.target()
					.orElseThrow(() -> new RuntimeException("Fact int storage can't be without Target"));

			var targetOccurence = innerMap.compute(target, mergeFunc);

			if (targetOccurence > occurrences) {

				clazz = target;
				occurrences = targetOccurence;
			}
			counter++;
		}

		var probability = occurrences * 1.d / counter;

		LikelihoodResult<T> result = new LikelihoodResult<T>(clazz, probability);
		return result;

	}

	private BiFunction<Target<T>, Integer, Integer> mergeFunc = (k, v) -> {
		if (v == null)
			return 1;
		return ++v;
	};

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

	public static class KNeighborEstimatorImplBuilder<T> {

		FactsRepository<T> factsRespository;
		BiFunction<TermsVector, TermsVector, Double> estimationFunction;
		public int complexity = 1;
		public int neighborns = 1;

		public KNeighborEstimatorImplBuilder(FactsRepository<T> factsRespository,
				BiFunction<TermsVector, TermsVector, Double> estimationFunction) {

			this.factsRespository = factsRespository;
			this.estimationFunction = estimationFunction;
		}

		public KNeighborEstimatorImplBuilder<T> with(Consumer<KNeighborEstimatorImplBuilder<T>> consumer) {
			consumer.accept(this);
			return this;
		}

		public KNeighborEstimatorImpl<T> build() {
			var result = new KNeighborEstimatorImpl<>(factsRespository, estimationFunction);
			result.complexity = complexity;
			result.neighborns = neighborns;
			return result;
		}
	}
}
