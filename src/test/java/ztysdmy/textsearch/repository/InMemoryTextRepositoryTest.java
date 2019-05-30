package ztysdmy.textsearch.repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textsearch.model.Document;
import ztysdmy.textsearch.model.TermsVectorBuilder;
import ztysdmy.textsearch.model.TextSegment;
import ztysdmy.textsearch.model.TextSegmentField;

public class InMemoryTextRepositoryTest {

	
	@Test
	public void shouldGetAllDocuments() throws Exception {
		
		var textRepository = InMemoryTextRepository.instance();
		var documents =  documents();
		textRepository.populate(documents);
		var  result = textRepository.get();
		var array = result.toArray(new Document[0]);
		Assert.assertEquals(2, array.length);
	}
	
	@Test
	public void shouldSortDocuments() throws Exception {
		
		var textRepository = InMemoryTextRepository.instance();
		var documents =  documents();
		var array1 = documents.toArray(new Document[0]);
		textRepository.populate(documents);
		var request = TermsVectorBuilder.build(TextSegment.from("test1"));
		var documents2 = textRepository.get(request);
		var array2 = documents2.toArray(new Document[0]);
		Assert.assertEquals(array2[0].identifier(), array1[1].identifier());
	}
	
	
	@SuppressWarnings("static-access")
	@Test
	public void shouldGetAllDocmentsInDifferentThreads() throws Exception {
		
		var documents = documents();
		var textRepository = InMemoryTextRepository.instance();
		
		var futureDocuments = CompletableFuture.supplyAsync(()->{
			
			@SuppressWarnings("unchecked")
			Collection<Document> documentSet = Collections.EMPTY_LIST;

			while (documentSet.isEmpty()) {
				
				documentSet = textRepository.get();
			}
			
			return documentSet;
		});
		
		
		CompletableFuture.runAsync(()->{
			try {
				Thread.currentThread().sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			textRepository.populate(documents);
		});
		
		Assert.assertTrue(!futureDocuments.get().isEmpty());
	}
	
	private Collection<Document> documents() {

		var result = List.of(document(TextSegment.from("test")), document(TextSegment.from("test1")));
		return result;
	}

	private Document document(TextSegment textSegment) {
		var field1 = new TextSegmentField("test", textSegment);
		var document = new Document();
		document.addField(field1);
		return document;
	}

}
