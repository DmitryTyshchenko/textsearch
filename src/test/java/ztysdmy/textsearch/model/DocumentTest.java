package ztysdmy.textsearch.model;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.Assert;
import org.junit.Test;

public class DocumentTest {

	@Test
	public void shouldCollectTextProviders() throws Exception {
		Document document = document();
		List<TextSegmentField> textProviders = document.textSegmentFields();
		Assert.assertEquals(1, textProviders.size());
	}

	@Test
	public void shouldSmoothEvaluation() throws Exception {

		TextSegmentField field1 = new TextSegmentField("test", TextSegment.from("test"));
		TermsVector vector1 = TermsVectorBuilder.build(field1);

		TermsVector vector2 = TermsVectorBuilder.build(field1.value());
		Assert.assertEquals(0.9d, vector1.eval(vector2), 0.d);
	}

	@Test
	public void shouldHaveUniqueIdentifier() throws Exception {

		CompletableFuture<Document> documentFuture1 = CompletableFuture.supplyAsync(() -> document());
		CompletableFuture<Document> documentFuture2 = CompletableFuture.supplyAsync(() -> document());

		Document document1 = documentFuture1.get();
		Document document2 = documentFuture2.get();
		Assert.assertNotEquals(document1.identifier(), document2.identifier());
	}

	private Document document() {

		Field<TextSegment> field1 = new TextSegmentField("test", TextSegment.from("test"));
		Field<Long> field2 = new Field<>("test2", 1l);
		Document document = new Document();
		document.addField(field1);
		document.addField(field2);
		return document;
	}
}
