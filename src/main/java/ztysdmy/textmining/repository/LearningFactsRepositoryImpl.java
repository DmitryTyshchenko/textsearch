package ztysdmy.textmining.repository;

import java.util.ArrayList;
import java.util.Collection;

import ztysdmy.textmining.model.Fact;

public class LearningFactsRepositoryImpl<T> implements LearningFactsRepository<T> {

	private final FactsRepository<T> factsRepository;

	private Float splitRatio = 0.5f;

	public LearningFactsRepositoryImpl(FactsRepository<T> factsRepository) {
		this.factsRepository = factsRepository;
	}

	public LearningFactsRepositoryImpl(FactsRepository<T> factsRepository, Float splitRatio) {
		this(factsRepository);

		if (splitRatio > 1.f) {

			throw new IllegalArgumentException("splitRatio can't be greater than 1.f");
		}
		this.splitRatio = splitRatio;
	}

	@Override
	public Collection<Fact<T>> learningSet() {
		var size = factsRepository.size();
		var threshold = threshold(size);
		return splitSet(0, threshold);
	}

	@Override
	public Collection<Fact<T>> testSet() {
		var size = factsRepository.size();
		var threshold = threshold(size);
		return splitSet(threshold, size);
	}

	private Collection<Fact<T>> splitSet(int startIndex, int endIndex) {

		var result = new ArrayList<Fact<T>>();

		@SuppressWarnings("unchecked")
		Fact<T>[] facts = factsRepository.getAll().toArray(new Fact[0]);

		int index = startIndex;

		while (index < endIndex) {
			result.add(facts[index]);
			index++;
		}

		return result;
	}

	private int threshold(int factSetSize) {
		return Math.round(factSetSize * splitRatio);
	}
}
