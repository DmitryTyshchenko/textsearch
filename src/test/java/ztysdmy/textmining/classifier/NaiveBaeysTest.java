package ztysdmy.textmining.classifier;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textmining.classifier.NaiveBaeys;
import ztysdmy.textmining.classifier.NaiveBaeys.TermStatistics;
import ztysdmy.textmining.model.Target;
import ztysdmy.textmining.model.Term;
import ztysdmy.textmining.model.TermsVectorBuilder;

public class NaiveBaeysTest {

	@Test
	public void testDenominator() throws Exception {

		var segment = "test";
		var termsVector = TermsVectorBuilder.build(segment, 1);

		NaiveBaeys<String> naiveBayes = new NaiveBaeys<>();
		naiveBayes.setTotalFacts(2);

		TermStatistics termStatistics = new TermStatistics();
		termStatistics.totalOccuriences = 1;

		naiveBayes.termsStatistics.put(new Term("test"), termStatistics);

		Double result = naiveBayes.denominator.apply(termsVector);
		Assert.assertEquals(0.5d, result.doubleValue(), 0.d);
	}

	@Test
	public void testPy() throws Exception {

		NaiveBaeys<String> naiveBayes = new NaiveBaeys<>();
		naiveBayes.setTotalFacts(2);
		Target<String> target = new Target<>("test");
		naiveBayes.addTarget(target);
		var result = naiveBayes.py.apply(target);
		Assert.assertEquals(0.5d, result.doubleValue(), 0.d);
	}

	@Test
	public void shoudCalculateTargetOccuriencies() throws Exception {

		Target<String> target = new Target<>("test");
		NaiveBaeys<String> naiveBayes = new NaiveBaeys<>();
		for (int i = 0; i < 10; i++) {
			naiveBayes.addTarget(target);
		}
		var result = naiveBayes.classes.get(target);
		Assert.assertEquals(Integer.valueOf(1), result);
	}

}
