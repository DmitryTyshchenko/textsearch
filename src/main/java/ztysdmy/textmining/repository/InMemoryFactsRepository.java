package ztysdmy.textmining.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Spliterators;
import java.util.Map.Entry;
import java.util.concurrent.locks.StampedLock;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import ztysdmy.textmining.model.Fact;

public class InMemoryFactsRepository<T> implements FactsRepository<T> {

	public InMemoryFactsRepository() {
	};

	private <R> R doWithOptimisticRead(Supplier<R> supplier) {

		long stamp = readWriteLock.tryOptimisticRead();
		R result = supplier.get();
		if (readWriteLock.validate(stamp)) {
			return result;
		}
		stamp = readWriteLock.readLock();
		try {
			result = supplier.get();
		} finally {
			readWriteLock.unlockRead(stamp);
		}
		return result;

	}

	private volatile HashMap<Long, Fact<T>> facts = new HashMap<>();

	@Override
	public void add(Collection<Fact<T>> facts) {

		long stamp = readWriteLock.writeLock();
		try {
			@SuppressWarnings("unchecked")
			var localFacts = (HashMap<Long, Fact<T>>)this.facts.clone();
			
			addToLocalFacts(facts, localFacts);
			this.facts = localFacts;

		} finally {
			readWriteLock.unlockWrite(stamp);
		}

	}

	private void addToLocalFacts(Collection<Fact<T>> facts, HashMap<Long, Fact<T>> localFacts) {

		for (Fact<T> fact : facts) {
			localFacts.put(fact.identifier(), fact);
		}
	}

	private final StampedLock readWriteLock = new StampedLock();

	@Override
	public void clear() {
		HashMap<Long, Fact<T>> newHashMap = new HashMap<>();
		long stamp = readWriteLock.writeLock();
		try {
			this.facts = newHashMap;
		} finally {
			readWriteLock.unlockWrite(stamp);
		}
	}

	@Override
	public int size() {
		return doWithOptimisticRead(() -> facts.size());
	}

	@Override
	public Iterator<Fact<T>> iterator() {
		var localFacts = doWithOptimisticRead(() -> facts);
		return new FactsRepositroyIterator<T>(localFacts.entrySet().iterator());
	}

	private static class FactsRepositroyIterator<T> implements Iterator<Fact<T>> {

		private final Iterator<Entry<Long, Fact<T>>> iterator;

		FactsRepositroyIterator(Iterator<Entry<Long, Fact<T>>> iterator) {

			this.iterator = iterator;
		}

		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public Fact<T> next() {

			return iterator.next().getValue();
		}

	}

	@Override
	public Stream<Fact<T>> stream() {

		var spliterator = Spliterators.spliteratorUnknownSize(iterator(), 0);

		return StreamSupport.stream(spliterator, false);
	}
}
