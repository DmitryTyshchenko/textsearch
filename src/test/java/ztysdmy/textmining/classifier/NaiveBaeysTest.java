package ztysdmy.textmining.classifier;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textmining.classifier.NaiveBaeys;
import ztysdmy.textmining.classifier.NaiveBaeys.TermStatistics;
import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.LikelihoodResult;
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
		termStatistics.occurrenciesInTarget(new Target<String>("y"));
		// termStatistics.totalOccuriences = 1;

		naiveBayes.termsStatistics.put(new Term("test"), termStatistics);

		Double result = naiveBayes.denominator.apply(termsVector);
		Assert.assertEquals(0.5d, result.doubleValue(), 0.d);
	}

	@Test
	public void testPy() throws Exception {

		NaiveBaeys<String> naiveBayes = new NaiveBaeys<>();
		naiveBayes.setTotalFacts(2);
		Target<String> target = new Target<>("test");
		naiveBayes.targetOccurrencies(target);
		var result = naiveBayes.py.apply(target);
		Assert.assertEquals(0.5d, result.doubleValue(), 0.d);
	}

	@Test
	public void shoudCalculateTargetOccuriencies() throws Exception {

		Target<String> target = new Target<>("test");
		NaiveBaeys<String> naiveBayes = new NaiveBaeys<>();
		withFor(10, ()->{
			naiveBayes.targetOccurrencies(target);
		});
		var result = naiveBayes.classes.get(target);
		Assert.assertEquals(Integer.valueOf(10), result);
	}

	@Test
	public void testPxc() throws Exception {

		NaiveBaeys<String> naiveBayes = new NaiveBaeys<>();
		naiveBayes.setTotalFacts(2);

		Target<String> target1 = new Target<>("target1");
		Target<String> target2 = new Target<>("target2");

		TermStatistics termStatistics = new TermStatistics();
		termStatistics.occurrenciesInTarget(target1);
		termStatistics.occurrenciesInTarget(target2);

		naiveBayes.termsStatistics.put(new Term("test"), termStatistics);

		var result = naiveBayes.pxc.apply(target1).apply(new Term("test"));
		Assert.assertEquals(0.5d, result.doubleValue(), 0.d);

	}

	@Test
	public void shouldChooseMax() throws Exception {

		NaiveBaeys<String> naiveBayes = new NaiveBaeys<>();
		var result1 = new LikelihoodResult<String>(new Target<>("a"), Double.valueOf(0.5d));
		var result2 = new LikelihoodResult<String>(new Target<>("b"), Double.valueOf(0.7d));
		var result = naiveBayes.chooseMax(result1, result2);
		var value = result.target().value();
		Assert.assertEquals("b", value);
	}

	@Test
	public void testLikelihood1() throws Exception {

		NaiveBaeys<String> naiveBayes = new NaiveBaeys<>();
		naiveBayes.setTotalFacts(1);

		Target<String> target1 = new Target<>("target1");

		naiveBayes.targetOccurrencies(target1);

		TermStatistics termStatistics1 = new TermStatistics();
		termStatistics1.occurrenciesInTarget(target1);

		naiveBayes.termsStatistics.put(new Term("test"), termStatistics1);

		var result = naiveBayes.likelihood(new Fact<String>("test"));
		Assert.assertEquals(1.0d, result.probability(), 0.d);
	}

	@Test
	public void testLikelihood2() throws Exception {

		NaiveBaeys<String> naiveBayes = new NaiveBaeys<>();
		naiveBayes.setTotalFacts(2);

		Target<String> target1 = new Target<>("target1");

		naiveBayes.targetOccurrencies(target1);

		Target<String> target2 = new Target<>("target2");

		naiveBayes.targetOccurrencies(target2);

		TermStatistics termStatistics1 = new TermStatistics();
		termStatistics1.occurrenciesInTarget(target1);
		termStatistics1.occurrenciesInTarget(target2);

		naiveBayes.termsStatistics.put(new Term("test"), termStatistics1);

		var result = naiveBayes.likelihood(new Fact<String>("test"));
		Assert.assertEquals(0.25d, result.probability(), 0.d);
	}

	@Test
	public void testLikelihood3() throws Exception {

		NaiveBaeys<String> naiveBayes = new NaiveBaeys<>();
		naiveBayes.setTotalFacts(10);

		Target<String> target1 = new Target<>("target1");

		withFor(5, ()->naiveBayes.targetOccurrencies(target1));
		
		Target<String> target2 = new Target<>("target2");

		withFor(5, ()->naiveBayes.targetOccurrencies(target2));

		TermStatistics termStatistics1 = new TermStatistics();
		termStatistics1.occurrenciesInTarget(target1);
		termStatistics1.occurrenciesInTarget(target2);

		naiveBayes.termsStatistics.put(new Term("test"), termStatistics1);

		TermStatistics termStatistics2 = new TermStatistics();
		termStatistics2.occurrenciesInTarget(target1);
		withFor(9, ()->{
			termStatistics2.occurrenciesInTarget(target2);
		});

		naiveBayes.termsStatistics.put(new Term("test2"), termStatistics2);
		var result = naiveBayes.likelihood(new Fact<String>("test2"));
		Assert.assertEquals("target2",result.target().value());
	}
	
	private void withFor(int end, Runnable action) {
		
		for (int i=0;i<end;i++) {
			action.run();
		}
	}
	
}
