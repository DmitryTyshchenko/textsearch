package ztysdmy.textmining.model;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textmining.model.TermsVectorBuilder;
import ztysdmy.textmining.model.TextSegment;

public class TermsVectorBuilderTest {

	@Test
	public void shouldCreateTermsVector1() throws Exception {
		var segment = TextSegment.from("This is a some test sentence!");
		var termsVector = TermsVectorBuilder.build(segment);
		termsVector.getTerm("is a").orElseThrow(() -> new RuntimeException("is a Not found"));
		termsVector.getTerm("a some test").orElseThrow(() -> new RuntimeException("a some Test Not found"));
		termsVector.getTerm("test").orElseThrow(() -> new RuntimeException("test Not found"));
		termsVector.getTerm("this is a").orElseThrow(() -> new RuntimeException("this is a Not found"));
	}

	@Test
	public void shuoldNormilizeText1() throws Exception {

		String[] before = { "Test", "A" };
		String[] after = TermsVectorBuilder.normilizer.apply(before);
		String[] expected = { "test", "a" };
		Assert.assertArrayEquals(expected, after);
	}
	
	@Test
	public void shouldRemoveLastSymbol() throws Exception {
		var test = "test!";
		var result = TermsVectorBuilder.punctuationNormilizer.apply(test);
		Assert.assertEquals("test", result);
	}
	
}
