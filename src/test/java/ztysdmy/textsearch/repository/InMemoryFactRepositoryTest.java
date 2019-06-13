package ztysdmy.textsearch.repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textsearch.model.Fact;
import ztysdmy.textsearch.model.TextSegment;
import ztysdmy.textsearch.model.TextSegmentField;

public class InMemoryFactRepositoryTest {

	
	@Test
	public void shouldGetAllDocuments() throws Exception {
		
		var textRepository = InMemoryFactRepository.instance();
		textRepository.clear();
		var documents =  documents();
		textRepository.populate(documents);
		var  result = textRepository.get();
		var array = result.toArray(new Fact[0]);
		Assert.assertEquals(2, array.length);
	}
	
	
	@SuppressWarnings("static-access")
	@Test
	public void shouldGetAllDocmentsInDifferentThreads() throws Exception {
		
		var documents = documents();
		var textRepository = InMemoryFactRepository.instance();
		textRepository.clear();
		var futureDocuments = CompletableFuture.supplyAsync(()->{
			
			@SuppressWarnings("unchecked")
			Collection<Fact> documentSet = Collections.EMPTY_LIST;

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
	
	private Collection<Fact> documents() {

		var result = List.of(document(TextSegment.from("test")), document(TextSegment.from("test1")));
		return result;
	}

	private Fact document(TextSegment textSegment) {
		var field1 = new TextSegmentField("test", textSegment);
		var document = new Fact();
		document.addField(field1);
		return document;
	}

}
