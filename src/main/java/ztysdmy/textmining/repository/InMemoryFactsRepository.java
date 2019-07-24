package ztysdmy.textmining.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.locks.StampedLock;
import java.util.function.Supplier;

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
	public void populate(Collection<Fact<T>> facts) {

		HashMap<Long, Fact<T>> localFacts = facts(facts);

		long stamp = readWriteLock.writeLock();
		try {

			this.facts = localFacts;

		} finally {
			readWriteLock.unlockWrite(stamp);
		}

	}

	private HashMap<Long, Fact<T>> facts(Collection<Fact<T>> facts) {

		HashMap<Long, Fact<T>> localFacts = new HashMap<>();
		for (Fact<T> fact : facts) {
			localFacts.put(fact.identifier(), fact);
		}

		return localFacts;
	}

	private final StampedLock readWriteLock = new StampedLock();

	@Override
	public void clear() {
		long stamp = readWriteLock.writeLock();
		try {
			this.facts = new HashMap<>();
		} finally {
			readWriteLock.unlockWrite(stamp);
		}
	}

	@Override
	public int size() {
		return doWithOptimisticRead(() -> facts.size());
	}


	@Override
	public RepositoryIterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
