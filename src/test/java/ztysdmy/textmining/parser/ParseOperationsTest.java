package ztysdmy.textmining.parser;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textmining.model.TextSegment;
import ztysdmy.textmining.parser.ParseOperations;

public class ParseOperationsTest {

	@Test
	public void shouldRemoveHtmlTags() throws Exception {

		var withHtmlValue = "<tag>test</tag>";
		var withoutHtmlSegment = ParseOperations.removeHtmlTagsOperation.apply(TextSegment.from(withHtmlValue));
		Assert.assertEquals("test", withoutHtmlSegment.toString());
	}

	@Test
	public void shouldParseSentence1() throws Exception {

		var candidate = "test! test";
		var sentences = ParseOperations.splitToSentencesOperation.apply(TextSegment.from(candidate));
		var array = sentences.toArray(new TextSegment[0]);
		Assert.assertEquals("test!", array[0].toString());
		Assert.assertEquals("test", array[1].toString());
	}

	@Test
	public void shouldParseSentence2() throws Exception {

		var candidate = "test? test";
		var sentences = ParseOperations.splitToSentencesOperation.apply(TextSegment.from(candidate));
		var array = sentences.toArray(new TextSegment[0]);
		Assert.assertEquals("test?", array[0].toString());
		Assert.assertEquals("test", array[1].toString());
	}

	@Test
	public void shouldParseSentence3() throws Exception {

		var candidate = "test. test";
		var sentences = ParseOperations.splitToSentencesOperation.apply(TextSegment.from(candidate));
		var array = sentences.toArray(new TextSegment[0]);
		Assert.assertEquals("test.", array[0].toString());
		Assert.assertEquals("test", array[1].toString());
	}
	
	@Test
	public void shouldParseSentence4() throws Exception {

		var candidate = "test... test";
		var sentences = ParseOperations.splitToSentencesOperation.apply(TextSegment.from(candidate));
		var array = sentences.toArray(new TextSegment[0]);
		Assert.assertEquals("test...", array[0].toString());
		Assert.assertEquals("test", array[1].toString());
	}

}
