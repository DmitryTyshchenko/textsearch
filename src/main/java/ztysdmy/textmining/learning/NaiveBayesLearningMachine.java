package ztysdmy.textmining.learning;

import ztysdmy.textmining.classifier.Classifier;
import ztysdmy.textmining.repository.FactsRepository;

public class NaiveBayesLearningMachine<T> implements Supervized<T> {

	private FactsRepository<T> factsRepository;
	private int complexity;
	
	public NaiveBayesLearningMachine(FactsRepository<T> factsRepository) {
		
		this(factsRepository, 1);
	}
	
    public NaiveBayesLearningMachine(FactsRepository<T> factsRepository, int complexity) {
		
		this.factsRepository = factsRepository;
		this.complexity = complexity;
	}
	
	@Override
	public Classifier<T> build() {
		// TODO Auto-generated method stub
		return null;
	}

}
