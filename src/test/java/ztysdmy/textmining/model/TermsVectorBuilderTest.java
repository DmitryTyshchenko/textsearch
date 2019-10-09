package ztysdmy.textmining.model;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textmining.model.TermsVectorBuilder;

public class TermsVectorBuilderTest {

	@Test
	public void shouldCreateTermsVector1() throws Exception {
		var segment = "This is a some test sentence!";
		var termsVector = TermsVectorBuilder.build(segment, 3);
		termsVector.getTerm(new Term("is a")).orElseThrow(() -> new RuntimeException("is a Not found"));
		termsVector.getTerm(new Term("a some test")).orElseThrow(() -> new RuntimeException("a some Test Not found"));
		termsVector.getTerm(new Term("test")).orElseThrow(() -> new RuntimeException("test Not found"));
		termsVector.getTerm(new Term("this is a")).orElseThrow(() -> new RuntimeException("this is a Not found"));
	}

	@Test
	public void shuoldNormilizeText1() throws Exception {

		String[] before = { "Test", "A" };
		String[] after = TermsVectorBuilder.normilize.apply(before);
		String[] expected = { "test", "a" };
		Assert.assertArrayEquals(expected, after);
	}
	
	@Test
	public void shouldRemoveLastSymbol() throws Exception {
		var test = "test!";
		var result = TermsVectorBuilder.punctuationNormilizer.apply(test);
		Assert.assertEquals("test", result);
	}
	
	@Test
	public void shouldSplitStringToWords() throws Exception {
		String[] result = TermsVectorBuilder.splitStringToWords.apply("sSome test, to spit string");
		Assert.assertTrue(5==result.length);
	}
	
	@Test
	public void shouldFilterEmptyWords() throws Exception {
		String[] test = {""};
 		String[] result = TermsVectorBuilder.filterEmptyWords.apply(test);
 		Assert.assertTrue(0==result.length);
	}
	
	@Test
	public void shouldIndicateThatRemovalOfLastCharIsNeeded() throws Exception {
		var result = TermsVectorBuilder.checkIfRemovalIsNedeed.apply(',');
		Assert.assertTrue(result.equals(TermsVectorBuilder.RemovalIsNeeded.YES));
	}
	
	@Test
	public void shouldReturnLastCharFromString() throws Exception {
		var result = TermsVectorBuilder.getLastCharacterFromString.apply("test");
		Assert.assertTrue(Character.valueOf('t').equals(result));
	}
}
