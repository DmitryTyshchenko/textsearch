package ztysdmy.textsearch.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.StampedLock;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import ztysdmy.textsearch.model.Fact;

public class InMemoryFactRepository implements FactRepository {

	private InMemoryFactRepository() {
	};

	private volatile static InMemoryFactRepository instance;

	public static InMemoryFactRepository instance() {

		if (instance == null) {
			synchronized (InMemoryFactRepository.class) {
				if (instance == null) {
					instance = new InMemoryFactRepository();
				}
			}
		}
		return instance;
	}

	@Override
	public Collection<Fact> get() {

		var localDocuments = facts;

		return get(() -> {
			return localDocuments.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList());

		});
	}

	private Collection<Fact> get(Supplier<List<Fact>> supplier) {
		List<Fact> result = new ArrayList<>();
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

	private volatile HashMap<Long, Fact> facts = new HashMap<>();

	@Override
	public void populate(Collection<Fact> documents) {

		HashMap<Long, Fact> localFacts = facts(documents);

		long stamp = readWriteLock.writeLock();
		try {

			this.facts = localFacts;

		} finally {
			readWriteLock.unlockWrite(stamp);
		}

	}

	private HashMap<Long, Fact> facts(Collection<Fact> facts) {

		HashMap<Long, Fact> localFacts = new HashMap<>();
		for (Fact fact : facts) {
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
