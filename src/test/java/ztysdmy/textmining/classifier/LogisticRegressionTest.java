package ztysdmy.textmining.classifier;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textmining.classifier.LogisticRegression.Monomial;
import ztysdmy.textmining.model.Term;
import ztysdmy.textmining.model.TermsVectorBuilder;

public class LogisticRegressionTest {
	
	@Test
	public void testSumOfMonomials() throws Exception {
		LogisticRegression logisticRegression = new LogisticRegression();
		logisticRegression.IDENTITY.updateWeight(1.d);
		Term term = new Term("a");
		Monomial monomial = new Monomial();
		monomial.updateWeight(1.d);
		logisticRegression.POLYNOMIAL.put(term, monomial);
		var terms = TermsVectorBuilder.build("a b v", 1);
		Assert.assertEquals(2.0d, logisticRegression.sumOfMonomials.apply(terms), 0.d);
	}
}
