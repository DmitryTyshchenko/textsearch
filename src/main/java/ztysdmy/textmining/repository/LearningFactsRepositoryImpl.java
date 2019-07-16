package ztysdmy.textmining.repository;

import java.util.Collection;

import ztysdmy.textmining.model.Fact;

public class LearningFactsRepositoryImpl<T> implements LearningFactsRepository<T> {

	private final FactsRepository<T> factsRepository;
	
	private Double splitRatio = 0.5d; 
	
	public LearningFactsRepositoryImpl(FactsRepository<T> factsRepository) {
		this.factsRepository = factsRepository;
	}
	
	public LearningFactsRepositoryImpl(FactsRepository<T> factsRepository, Double splitRatio) {
		this(factsRepository);
		this.splitRatio = splitRatio;
	}
	
	@Override
	public Collection<Fact<T>> learningSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Fact<T>> testSet() {
		// TODO Auto-generated method stub
		return null;
	}
}
