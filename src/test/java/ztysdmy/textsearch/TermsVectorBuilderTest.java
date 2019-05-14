package ztysdmy.textsearch;

import org.junit.Test;
import ztysdmy.textsearch.TermsVectorBuilder;
import ztysdmy.textsearch.model.Segment;

public class TermsVectorBuilderTest {

	@Test
	public void shouldCreateTermsVector1() throws Exception {
		var segment = Segment.sentence("This is a some test sentence!");
		var termsVector = TermsVectorBuilder.build(segment);
		termsVector.getTerm("is a").orElseThrow(()->new RuntimeException("is a Not found"));
		termsVector.getTerm("a some test").orElseThrow(()->new RuntimeException("a some Test Not found"));
		termsVector.getTerm("test").orElseThrow(()->new RuntimeException("test Not found"));
	}
	
}
