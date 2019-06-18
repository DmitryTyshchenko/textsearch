package ztysdmy.textmining.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.StampedLock;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import ztysdmy.textmining.model.Fact;

public class InMemoryFactRepository<T> implements FactRepository<T> {

	public InMemoryFactRepository() {
	};

	@Override
	public Collection<Fact<T>> get() {

		var localDocuments = facts;

		return get(() -> {
			return localDocuments.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList());

		});
	}

	private Collection<Fact<T>> get(Supplier<List<Fact<T>>> supplier) {
		List<Fact<T>> result = new ArrayList<>();
		long stamp = readWriteLock.tryOptimisticRead();

		result = supplier.get();

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
}
