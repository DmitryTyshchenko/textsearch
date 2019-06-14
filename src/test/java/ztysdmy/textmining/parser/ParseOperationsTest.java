package ztysdmy.textmining.parser;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textmining.model.Text;
import ztysdmy.textmining.parser.ParseOperations;

public class ParseOperationsTest {

	@Test
	public void shouldRemoveHtmlTags() throws Exception {

		var withHtmlValue = "<tag>test</tag>";
		var withoutHtmlSegment = ParseOperations.removeHtmlTagsOperation.apply(Text.from(withHtmlValue));
		Assert.assertEquals("test", withoutHtmlSegment.toString());
	}

	@Test
	public void shouldParseSentence1() throws Exception {

		var candidate = "test! test";
		var sentences = ParseOperations.splitToSentencesOperation.apply(Text.from(candidate));
		var array = sentences.toArray(new Text[0]);
		Assert.assertEquals("test!", array[0].toString());
		Assert.assertEquals("test", array[1].toString());
	}

	@Test
	public void shouldParseSentence2() throws Exception {

		var candidate = "test? test";
		var sentences = ParseOperations.splitToSentencesOperation.apply(Text.from(candidate));
		var array = sentences.toArray(new Text[0]);
		Assert.assertEquals("test?", array[0].toString());
		Assert.assertEquals("test", array[1].toString());
	}

	@Test
	public void shouldParseSentence3() throws Exception {

		var candidate = "test. test";
		var sentences = ParseOperations.splitToSentencesOperation.apply(Text.from(candidate));
		var array = sentences.toArray(new Text[0]);
		Assert.assertEquals("test.", array[0].toString());
		Assert.assertEquals("test", array[1].toString());
	}
	
	@Test
	public void shouldParseSentence4() throws Exception {

		var candidate = "test... test";
		var sentences = ParseOperations.splitToSentencesOperation.apply(Text.from(candidate));
		var array = sentences.toArray(new Text[0]);
		Assert.assertEquals("test...", array[0].toString());
		Assert.assertEquals("test", array[1].toString());
	}

}
