package ztysdmy.textmining.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import ztysdmy.textmining.functions.TanimotoDistance;
import ztysdmy.textmining.model.TermsVector;
import ztysdmy.textmining.model.TermsVectorBuilder;

public class TermsVectorTest {

	@Test
	public void shouldCreateTestTerm() throws Exception {

		var terms = testTermVector();
		assertNotNull(terms.getTerm(new Term("test")).get());
	}

	private TermsVector testTermVector() {

		var testKey = "test";
		var termsVector = new TermsVector();

		for (int i = 0; i < 3; i++) {
			termsVector.addTerm(testKey);
		}

		return termsVector;
	}

	// 2 termsVectors should be equal
	@Test
	public void shouldCalculateDistance() throws Exception {

		TermsVector vector1 = testTermVector();
		TermsVector vector2 = testTermVector();

		var distance = vector1.eval(vector2, new TanimotoDistance());
		assertEquals(1.d, distance, 0.d);
	}

	// 2 termsVectors should not be equal
	@Test
	public void shouldCalculateDistance2() throws Exception {
		var termsVector1 = TermsVectorBuilder.build("test", 0);
		var termsVector2 = TermsVectorBuilder.build("test2", 0);
		var distance = termsVector1.eval(termsVector2, new TanimotoDistance());
		assertEquals(0.d, distance, 0.d);
	}
}
