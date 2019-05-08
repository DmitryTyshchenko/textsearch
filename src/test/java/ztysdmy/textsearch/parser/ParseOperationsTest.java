package ztysdmy.textsearch.parser;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textsearch.model.Segment;

public class ParseOperationsTest {

	@Test
	public void shouldRemoveHtmlTags() throws Exception {

		var withHtmlValue = "<tag>test</tag>";
		var withoutHtmlSegment = ParseOperations.removeHtmlTagsOperation.apply(Segment.sentence(withHtmlValue));
		Assert.assertEquals("test", withoutHtmlSegment.toString());
	}
}
