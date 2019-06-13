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

/**	@Override
	public Collection<Fact> likelihood(TermsVector termsVector) {

		return get(() -> {

			ArrayList<TermsVectorEntityWithWeight> temp = new ArrayList<>();
			
			var localTermsVectorEntities = termsVectorEntities;
			
			var localDocuments = documents;
			
			for (TermsVectorEntity termsVectorEntity : localTermsVectorEntities) {

				Double distance = termsVectorEntity.termsVector.eval(termsVector);
				TermsVectorEntityWithWeight e = new TermsVectorEntityWithWeight(termsVectorEntity, distance);
				temp.add(e);
			}

			Collections.sort(temp, termsVectorWithWeightComparator);
			
			return temp.stream().map(a -> localDocuments.get(a.termsVectorEntity.documentId))
					.collect(Collectors.toList());

		});

	}
**/
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

		HashMap<Long, Fact> localDocuments = new HashMap<>();
		for (Fact document : facts) {
			localDocuments.put(document.identifier(), document);
		}

		return localDocuments;
	}

/**	private ArrayList<TermsVectorEntity> termsVectors(Collection<Fact> documents) {

		ArrayList<TermsVectorEntity> LocaltermsVectorEntities = new ArrayList<>();

		documents.stream().forEach(document -> {
			LocaltermsVectorEntities.addAll(termsVectors(document));
		});

		return LocaltermsVectorEntities;
	}**/

/**	private List<TermsVectorEntity> termsVectors(Fact document) {
		List<TextSegmentField> textSegmentFields = document.textSegmentFields();
		List<TermsVector> termsVectors = termsVectors(textSegmentFields);
		List<TermsVectorEntity> termsVectorsEntities = termsVectorsEntities(document.identifier(), termsVectors);
		return termsVectorsEntities;
	}**/

/**	private List<TermsVector> termsVectors(List<TextSegmentField> fields) {

		return fields.stream().map(field -> build(field)).collect(Collectors.toList());
	}**/

/**	private List<TermsVectorEntity> termsVectorsEntities(Long documentId, List<TermsVector> termsVectors) {

		return termsVectors.stream().map(termsVector -> new TermsVectorEntity(termsVector, documentId))
				.collect(Collectors.toList());

	}
**/
/**	private static class TermsVectorEntity {

		final TermsVector termsVector;
		final Long documentId;

		TermsVectorEntity(TermsVector termsVector, Long documentId) {
			this.termsVector = termsVector;
			this.documentId = documentId;
		}
	}
**/
	/**private static class TermsVectorEntityWithWeight {
		final TermsVectorEntity termsVectorEntity;
		final Double weight;

		TermsVectorEntityWithWeight(TermsVectorEntity termsVectorEntity, Double weight) {

			this.termsVectorEntity = termsVectorEntity;
			this.weight = weight;
		}
		
		@Override
		public boolean equals(Object o) {

			if (o == this) {
				return true;
			}

			if (!(o instanceof TermsVectorEntityWithWeight)) {
				return false;
			}

			TermsVectorEntityWithWeight c = (TermsVectorEntityWithWeight) o;

			return weight.equals(c.weight);

		}

	}**/

	private final StampedLock readWriteLock = new StampedLock();

	//private final Comparator<TermsVectorEntityWithWeight> termsVectorWithWeightComparator = (a, b) -> a.weight < b.weight ? 1
	//		: a.weight == b.weight ? 0 : -1;

	@Override
	public void clear() {
		 this.facts = new HashMap<>();
	}
}
