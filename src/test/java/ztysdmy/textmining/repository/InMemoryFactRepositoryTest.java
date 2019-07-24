package ztysdmy.textmining.repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.Target;
import ztysdmy.textmining.repository.InMemoryFactsRepository;

public class InMemoryFactRepositoryTest {

	
	@SuppressWarnings("static-access")
	@Test
/**	public void shouldGetAllDocmentsInDifferentThreads() throws Exception {

		var documents = facts();
		var textRepository = new InMemoryFactsRepository<Double>();
		textRepository.clear();
		var futureDocuments = CompletableFuture.supplyAsync(() -> {

			@SuppressWarnings("unchecked")
			Collection<Fact<Double>> documentSet = Collections.EMPTY_LIST;

			while (documentSet.isEmpty()) {

				documentSet = textRepository.getAll();
			}

			return documentSet;
		});

		CompletableFuture.runAsync(() -> {
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
**/
	private Collection<Fact<Double>> facts() {

		var result = List.of(fact("test"), fact("test1"));
		return result;
	}

	private Fact<Double> fact(String value) {
		var document = new Fact<Double>(value, new Target<Double>(1.d));

		return document;
	}

	@Test
	public void shouldGetSize() throws Exception {
		var textRepository = new InMemoryFactsRepository<Double>();
		textRepository.clear();
		textRepository.populate(facts());
		var size = textRepository.size();
		Assert.assertEquals(2, size);
	}

}
