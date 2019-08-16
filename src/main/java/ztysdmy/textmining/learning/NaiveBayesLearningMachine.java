package ztysdmy.textmining.learning;

import ztysdmy.textmining.classifier.Classifier;
import ztysdmy.textmining.classifier.NaiveBaeys;
import ztysdmy.textmining.repository.FactsRepository;

public class NaiveBayesLearningMachine<T> implements Supervized<T> {

	private FactsRepository<T> facts;
	private int complexity;
	
	public NaiveBayesLearningMachine(FactsRepository<T> factsRepository) {
		
		this(factsRepository, 1);
	}
	
    public NaiveBayesLearningMachine(FactsRepository<T> factsRepository, int complexity) {
		
		this.facts = factsRepository;
		this.complexity = complexity;
	}
	
	@Override
	public Classifier<T> build() {
		
		var result = new NaiveBaeys<T>(this.complexity);
		
		facts.stream().forEach(fact->{
			result.collectFactStatistics(fact);
		});
		return result;
	}
	
}
