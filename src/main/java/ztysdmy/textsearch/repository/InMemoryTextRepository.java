package ztysdmy.textsearch.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import ztysdmy.textsearch.model.Document;
import ztysdmy.textsearch.model.TermsVector;
import ztysdmy.textsearch.model.TextSegmentField;

import static ztysdmy.textsearch.model.TermsVectorBuilder.build;

public class InMemoryTextRepository implements TextRepository {

	private InMemoryTextRepository() {
	};

	private volatile static InMemoryTextRepository instance;

	public static InMemoryTextRepository instance() {

		if (instance == null) {
			synchronized (InMemoryTextRepository.class) {
				if (instance == null) {
					instance = new InMemoryTextRepository();
				}
			}
		}
		return instance;
	}

	@Override
	public Collection<Document> get(TermsVector termsVector) {

		return get(() -> {

			ArrayList<TermsVectorEntityWithWeight> temp = new ArrayList<>();
			
			for (TermsVectorEntity termsVectorEntity : termsVectorEntities) {

				Double distance = termsVectorEntity.termsVector.eval(termsVector);
				TermsVectorEntityWithWeight e = new TermsVectorEntityWithWeight(termsVectorEntity, distance);
				temp.add(e);
			}

			Collections.sort(temp, termsVectorWithWeightComparator);
			
			return temp.stream().map(a -> this.documents.get(a.termsVectorEntity.documentId))
					.collect(Collectors.toList());

		});

	}

	@Override
	public Collection<Document> get() {
		return get(() -> {
			return this.documents.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList());

		});
	}

	private Collection<Document> get(Supplier<List<Document>> supplier) {
		List<Document> result = new ArrayList<>();
		readWriteLock.readLock().lock();
		try {

			result = supplier.get();

		} finally {
			readWriteLock.readLock().unlock();
		}
		return result;

	}

	private ArrayList<TermsVectorEntity> termsVectorEntities = new ArrayList<>();
	private HashMap<Long, Document> documents = new HashMap<>();

	@Override
	public void populate(Collection<Document> documents) {

		ArrayList<TermsVectorEntity> LocaltermsVectorEntities = termsVectors(documents);
		HashMap<Long, Document> localDocuments = documents(documents);

		readWriteLock.writeLock().lock();
		try {

			this.documents = localDocuments;
			this.termsVectorEntities = LocaltermsVectorEntities;

		} finally {
			readWriteLock.writeLock().unlock();
		}

	}

	private HashMap<Long, Document> documents(Collection<Document> documents) {

		HashMap<Long, Document> localDocuments = new HashMap<>();
		for (Document document : documents) {
			localDocuments.put(document.identifier(), document);
		}

		return localDocuments;
	}

	private ArrayList<TermsVectorEntity> termsVectors(Collection<Document> documents) {

		ArrayList<TermsVectorEntity> LocaltermsVectorEntities = new ArrayList<>();

		documents.stream().forEach(document -> {
			LocaltermsVectorEntities.addAll(termsVectors(document));
		});

		return LocaltermsVectorEntities;
	}

	private List<TermsVectorEntity> termsVectors(Document document) {
		List<TextSegmentField> textSegmentFields = document.textSegmentFields();
		List<TermsVector> termsVectors = termsVectors(textSegmentFields);
		List<TermsVectorEntity> termsVectorsEntities = termsVectorsEntities(document.identifier(), termsVectors);
		return termsVectorsEntities;
	}

	private List<TermsVector> termsVectors(List<TextSegmentField> fields) {

		return fields.stream().map(field -> build(field)).collect(Collectors.toList());
	}

	private List<TermsVectorEntity> termsVectorsEntities(Long documentId, List<TermsVector> termsVectors) {

		return termsVectors.stream().map(termsVector -> new TermsVectorEntity(termsVector, documentId))
				.collect(Collectors.toList());

	}

	private static class TermsVectorEntity {

		final TermsVector termsVector;
		final Long documentId;

		TermsVectorEntity(TermsVector termsVector, Long documentId) {
			this.termsVector = termsVector;
			this.documentId = documentId;
		}
	}

	private static class TermsVectorEntityWithWeight {
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

	}

	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	private final Comparator<TermsVectorEntityWithWeight> termsVectorWithWeightComparator = (a, b) -> a.weight < b.weight ? 1
			: a.weight == b.weight ? 0 : -1;
}
