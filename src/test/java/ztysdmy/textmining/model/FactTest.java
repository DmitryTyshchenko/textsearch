package ztysdmy.textmining.model;

import java.util.concurrent.CompletableFuture;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textmining.model.Fact;

public class FactTest {

	@Test
	public void shouldHaveUniqueIdentifier() throws Exception {

		CompletableFuture<Fact<?>> factFuture1 = CompletableFuture.supplyAsync(() -> fact());
		CompletableFuture<Fact<?>> factFuture2 = CompletableFuture.supplyAsync(() -> fact());

		Fact<?> fact1 = factFuture1.get();
		Fact<?> fact2 = factFuture2.get();
		Assert.assertNotEquals(fact1.identifier(), fact2.identifier());
	}

	private Fact<?> fact() {

		Fact<?> fact = new Fact<>("test", new Target<Double>(1.0d));
		return fact;
	}
		
}
