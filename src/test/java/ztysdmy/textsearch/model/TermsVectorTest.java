package ztysdmy.textsearch.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TermsVectorTest {

	@Test
	public void shouldCreateTestTerm() throws Exception {

		var term = testTermVector().terms.get("test");
		assertEquals(3, term.occurances());
	}

	
	private TermsVector testTermVector() {

		var testKey = "test";
		var termsVector = new TermsVector();

		for (int i = 0; i < 3; i++) {
			termsVector.createOrUpdateTerm(testKey);
		}

		return termsVector;
	}
	
	//2 termsVectors should be equal
	@Test
	public void shouldCalculateDistance() throws Exception {
		
		TermsVector vector1 = testTermVector();
		TermsVector vector2 = testTermVector();
		
		var distance = vector1.eval(vector2);
		assertEquals(1.d, distance, 0.d);
	}
	
	//2 termsVectors should not be equal
	@Test
	public void shouldCalculateDistance2() throws Exception {
		var termsVector1 = TermsVectorBuilder.build(TextSegment.from("test"), 0);
		var termsVector2 = TermsVectorBuilder.build(TextSegment.from("test2"), 0);
		var distance = termsVector1.eval(termsVector2);
		assertEquals(0.d, distance, 0.d);
	}
}
