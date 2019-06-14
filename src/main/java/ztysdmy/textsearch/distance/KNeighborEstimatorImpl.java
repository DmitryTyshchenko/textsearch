package ztysdmy.textsearch.distance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.BiFunction;

import ztysdmy.textsearch.model.Fact;
import ztysdmy.textsearch.model.LikelihoodResult;
import ztysdmy.textsearch.model.TermsVector;
import ztysdmy.textsearch.model.TermsVectorBuilder;
import ztysdmy.textsearh.repository.query.GetAllFactsQuery;

public class KNeighborEstimatorImpl implements KNeighborEstimator {

	private final BiFunction<TermsVector, TermsVector, Double> estimationFunction;

	int complexity;

	public KNeighborEstimatorImpl(BiFunction<TermsVector, TermsVector, Double> estimationFunction, int complexity) {
		this.estimationFunction = estimationFunction;
		this.complexity = complexity;
	}

	public KNeighborEstimatorImpl() {
		this(new TanimotoDistance(), 3);
	}

	@Override
	public Collection<LikelihoodResult> likelihood(Fact input) {

		var toEvalTermsVector = TermsVectorBuilder.build(input, this.complexity);

		Collection<Fact> facts = facts();
		ArrayList<LikelihoodResult> result = new ArrayList<>();

		for (Fact fact : facts) {

			var termsVector = TermsVectorBuilder.build(fact, this.complexity);

			Double weight = termsVector.eval(toEvalTermsVector, estimationFunction);
			result.add(new LikelihoodResult(fact, weight));

		}
		Collections.sort(result, resultComparator);
		return result;
	}

	private Collection<Fact> facts() {

		GetAllFactsQuery qutAllQuery = new GetAllFactsQuery();

		return qutAllQuery.query();
	}

	private final Comparator<LikelihoodResult> resultComparator = (a, b) -> a.probability() < b.probability() ? 1
			: a.probability() == b.probability() ? 0 : -1;

}
