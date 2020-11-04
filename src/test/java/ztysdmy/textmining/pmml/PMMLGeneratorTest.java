package ztysdmy.textmining.pmml;

import static ztysdmy.textmining.pmml.PMMLGenerator.marshal;


import org.junit.Test;

import ztysdmy.textmining.classifier.LogisticRegression;
import ztysdmy.textmining.model.Term;



public class PMMLGeneratorTest {

	@Test
	public void shoulMarchalToString() throws Exception {
		
		var logisticRegression = new LogisticRegression();
		
		logisticRegression.addTermToPolynomIfAbsent(new Term("a"));
		
		
		System.out.println(logisticRegression.toPMML("Test", "Test", "hello"));
	}
	
}
