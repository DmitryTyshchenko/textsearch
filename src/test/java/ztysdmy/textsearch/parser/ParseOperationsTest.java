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

	@Test
	public void shouldParseSentence1() throws Exception {

		var candidate = "test!test";
		var sentences = ParseOperations.spitToSentencesOperation.apply(Segment.sentence(candidate));
		var array = sentences.toArray(new Segment[0]);
		Assert.assertEquals("test!", array[0].toString());
		Assert.assertEquals("test", array[1].toString());
	}

	@Test
	public void shouldParseSentence2() throws Exception {

		var candidate = "test?test";
		var sentences = ParseOperations.spitToSentencesOperation.apply(Segment.sentence(candidate));
		var array = sentences.toArray(new Segment[0]);
		Assert.assertEquals("test?", array[0].toString());
		Assert.assertEquals("test", array[1].toString());
	}

	@Test
	public void shouldParseSentence3() throws Exception {

		var candidate = "test.test";
		var sentences = ParseOperations.spitToSentencesOperation.apply(Segment.sentence(candidate));
		var array = sentences.toArray(new Segment[0]);
		Assert.assertEquals("test.", array[0].toString());
		Assert.assertEquals("test", array[1].toString());
	}
	
	@Test
	public void shouldParseSentence4() throws Exception {

		var candidate = "test...test";
		var sentences = ParseOperations.spitToSentencesOperation.apply(Segment.sentence(candidate));
		var array = sentences.toArray(new Segment[0]);
		Assert.assertEquals("test...", array[0].toString());
		Assert.assertEquals("test", array[1].toString());
	}

}
