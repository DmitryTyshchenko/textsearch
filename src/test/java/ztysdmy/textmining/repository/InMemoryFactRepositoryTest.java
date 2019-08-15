package ztysdmy.textmining.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.Target;
import ztysdmy.textmining.repository.InMemoryFactsRepository;

public class InMemoryFactRepositoryTest {

	@SuppressWarnings("static-access")
	@Test
	public void shouldPopulateAndGetFactsInDifferentThreads() throws Exception {

		var documents = facts();
		var textRepository = new InMemoryFactsRepository<Double>();
		textRepository.clear();
		
		Supplier<Collection<Fact<Double>>> supplier = () -> {

			Collection<Fact<Double>> documentSet = new ArrayList<>();

			while (documentSet.isEmpty()) {

				var iterator = textRepository.iterator();
				while (iterator.hasNext()) {
					documentSet.add(iterator.next());
				}
			}

			return documentSet;
		};

		
		var futureDocuments = CompletableFuture.supplyAsync(supplier);
		var futureDocuments1 = CompletableFuture.supplyAsync(supplier);
		var futureDocuments2 = CompletableFuture.supplyAsync(supplier);
		var futureDocuments3 = CompletableFuture.supplyAsync(supplier);
		
		
		CompletableFuture.runAsync(() -> {
			
			try {
				Thread.currentThread().sleep(2000);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			textRepository.add(documents);
			
		});

		Assert.assertTrue(!futureDocuments.get().isEmpty());
		Assert.assertTrue(!futureDocuments1.get().isEmpty());
		Assert.assertTrue(!futureDocuments2.get().isEmpty());
		Assert.assertTrue(!futureDocuments3.get().isEmpty());
	}

	private Collection<Fact<Double>> facts() {

		var result = List.of(fact("test"), fact("test1"), fact("test2"), fact("test3"), fact("test4"), fact("test5"));
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
		textRepository.add(facts());
		var size = textRepository.size();
		Assert.assertEquals(6, size);
	}

}
