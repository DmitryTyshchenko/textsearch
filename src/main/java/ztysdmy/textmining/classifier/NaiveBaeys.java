package ztysdmy.textmining.classifier;

import java.util.HashMap;

import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.LikelihoodResult;
import ztysdmy.textmining.model.Target;
import ztysdmy.textmining.model.Term;
import ztysdmy.textmining.model.TermsVector;
import ztysdmy.textmining.model.TermsVectorBuilder;

public class NaiveBaeys<T> implements Classifier<T> {

	HashMap<Term, TermStatistics<T>> termsStatistics = new HashMap<>();
	
	//keeps Target classes and their probabilities
	HashMap<Target<T>, Double> classes = new HashMap<>();
	
	int complexity = 1;
	
	@Override
	public LikelihoodResult<T> likelihood(Fact<T> input) {
		// TODO Auto-generated method stub
		TermsVector factTerms = TermsVectorBuilder.build(input, this.complexity);
		
		//classes.keySet().
		
		
		return null;
	}

	private LikelihoodResult<T> likelihood(Fact<T> input, Target<T> target) {
		return null;
	}
	
	static class TermStatistics<T> {
		
		HashMap<Target<T>, Integer> inTheClass = new HashMap<>();
		
		int totalOccuriences;
		
	}
}
