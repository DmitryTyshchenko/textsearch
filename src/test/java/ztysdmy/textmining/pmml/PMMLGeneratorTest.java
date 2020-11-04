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
		
		var builder = new PMML.Builder();
		var header = new Header("Test", "Test");
		var pmml = builder.setHeader(header).setDataDictionary(logisticRegression.dataDictionary()).build();
		System.out.println(marshal(pmml));
	}
	
}
