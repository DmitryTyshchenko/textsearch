package ztysdmy.textsearch.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TermsVectorTest {

	@Test
	public void shouldCreateTestTerm() throws Exception {

		String testKey = "test";
		TermsVector termsVector = new TermsVector(new Segment());

		for (int i = 0; i < 3; i++) {
			termsVector.createOrUpdateTerm(testKey);
		}

		Term term = termsVector.terms.get("test");
		assertEquals(3, term.occurances());
	}

}
