package ztysdmy.textmining.model;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.Field;
import ztysdmy.textmining.model.TextSegment;
import ztysdmy.textmining.model.TextSegmentField;

public class FactTest {

	@Test
	public void shouldCollectTextProviders() throws Exception {
		Fact document = fact();
		List<TextSegmentField> textProviders = document.textSegmentFields();
		Assert.assertEquals(1, textProviders.size());
	}
	

	@Test
	public void shouldHaveUniqueIdentifier() throws Exception {

		CompletableFuture<Fact> documentFuture1 = CompletableFuture.supplyAsync(() -> fact());
		CompletableFuture<Fact> documentFuture2 = CompletableFuture.supplyAsync(() -> fact());

		Fact document1 = documentFuture1.get();
		Fact document2 = documentFuture2.get();
		Assert.assertNotEquals(document1.identifier(), document2.identifier());
	}

	private Fact fact() {

		Field<TextSegment> field1 = new TextSegmentField("test", TextSegment.from("test"));
		Field<Long> field2 = new Field<>("test2", 1l);
		Fact fact = new Fact();
		fact.addField(field1);
		fact.addField(field2);
		return fact;
	}
}
