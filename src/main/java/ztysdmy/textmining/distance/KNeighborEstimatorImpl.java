package ztysdmy.textmining.distance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.BiFunction;

import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.LikelihoodResult;
import ztysdmy.textmining.model.TermsVector;
import ztysdmy.textmining.model.TermsVectorBuilder;
import ztysdmy.textmining.repository.FactRepository;
import ztysdmy.textmining.repository.query.GetAllFactsQuery;

public class KNeighborEstimatorImpl<T> implements KNeighborEstimator<T> {

	private final BiFunction<TermsVector, TermsVector, Double> estimationFunction;

	private FactRepository<T> factsRespository;
	
	int complexity;

	public KNeighborEstimatorImpl(FactRepository<T> factsRespository, BiFunction<TermsVector, TermsVector, Double> estimationFunction, int complexity) {
		this.estimationFunction = estimationFunction;
		this.complexity = complexity;
		this.factsRespository = factsRespository;
	}


	@Override
	public Collection<LikelihoodResult<T>> likelihood(Fact<T> input) {

		var toEvalTermsVector = TermsVectorBuilder.build(input, this.complexity);
		
		ArrayList<LikelihoodResult<T>> result = new ArrayList<>();

		for (Fact<T> fact : facts()) {

			var termsVector = TermsVectorBuilder.build(fact, this.complexity);

			Double weight = termsVector.eval(toEvalTermsVector, this.estimationFunction);
			result.add(new LikelihoodResult<>(fact, weight));

		}
		Collections.sort(result, this.resultComparator);
		return result;
	}

	private Collection<Fact<T>> facts() {

		GetAllFactsQuery<T> qutAllQuery = new GetAllFactsQuery<>(factsRespository);

		return qutAllQuery.query();
	}

	private final Comparator<LikelihoodResult<T>> resultComparator = (a, b) -> a.probability() < b.probability() ? 1
			: a.probability() == b.probability() ? 0 : -1;

}
