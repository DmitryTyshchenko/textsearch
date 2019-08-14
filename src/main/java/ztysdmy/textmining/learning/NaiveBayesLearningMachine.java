package ztysdmy.textmining.learning;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import ztysdmy.textmining.classifier.Classifier;
import ztysdmy.textmining.classifier.NaiveBaeys;
import ztysdmy.textmining.model.Fact;
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
		
		var result = new NaiveBaeys<T>(this.complexity);
		
		Iterator<Fact<T>> factsIterator = factsRepository.iterator();
		
		while (factsIterator.hasNext()) {
			 var fact = factsIterator.next();
		}
		// TODO Auto-generated method stub
		return result;
	}
	
	private static <T> Stream<T> getStreamFromIterator(Iterator<T> iterator) 
    { 
        var spliterator = Spliterators 
                              .spliteratorUnknownSize(iterator, 0); 
 
        return StreamSupport.stream(spliterator, false); 
    } 
}
