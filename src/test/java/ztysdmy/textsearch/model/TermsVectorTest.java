package ztysdmy.textsearch.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TermsVectorTest {

	@Test
	public void shouldCreateTestTerm() throws Exception {

		Term term = testTermVector().terms.get("test");
		assertEquals(3, term.occurances());
	}

	
	private TermsVector testTermVector() {

		String testKey = "test";
		TermsVector termsVector = new TermsVector(Segment.sentence("test"));

		for (int i = 0; i < 3; i++) {
			termsVector.createOrUpdateTerm(testKey);
		}

		return termsVector;
	}
}
