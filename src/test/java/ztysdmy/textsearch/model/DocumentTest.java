package ztysdmy.textsearch.model;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;


public class DocumentTest {

	@Test
	public void shouldCollectTextProviders() throws Exception {
		
		Field<TextSegment> field1 =  new TextSegmentField("test", TextSegment.from("test"));
		Field<Long> field2 =  new Field<>("test2", 1l);
		Document document = new Document();
		document.addField(field1);
		document.addField(field2);
		List<TextSegmentField> textProviders = document.textProviders();
		Assert.assertEquals(1, textProviders.size());
	}
	
	@Test
	public void shouldSmoothEvaluation() throws Exception {
		
		TextSegmentField field1 =  new TextSegmentField("test", TextSegment.from("test"));
		TermsVector vector1 = TermsVectorBuilder.build(field1);
		
		TermsVector vector2 = TermsVectorBuilder.build(field1.value());
		Assert.assertEquals(0.9d, vector1.eval(vector2), 0.d);
	}
}
