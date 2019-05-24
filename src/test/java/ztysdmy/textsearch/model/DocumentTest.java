package ztysdmy.textsearch.model;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;


public class DocumentTest {

	@Test
	public void shouldCollectTextProviders() {
		
		Field<Segment> field1 =  new Field<>("test", Segment.from("test"));
		Field<Long> field2 =  new Field<>("test2", 1l);
		Document document = new Document();
		document.addField(field1);
		document.addField(field2);
		List<TextProvider> textProviders = document.textProviders();
		Assert.assertEquals(1, textProviders.size());
	}
}
