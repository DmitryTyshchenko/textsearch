package ztysdmy.textsearch.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import ztysdmy.textsearch.model.Document;
import ztysdmy.textsearch.model.TermsVector;
import ztysdmy.textsearch.model.TextSegmentField;

import static ztysdmy.textsearch.model.TermsVectorBuilder.build;

public class InMemoryTextRepository implements TextRepository {

	private InMemoryTextRepository() {
	};

	private static InMemoryTextRepository instance;

	public synchronized static InMemoryTextRepository instance() {

		if (instance == null) {

			instance = new InMemoryTextRepository();
		}

		return instance;
	}

	@Override
	public SortedSet<Document> get(TermsVector termsVector) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Document> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	private ArrayList<TermsVectorEntity> termsVectorEntities = new ArrayList<>();

	@Override
	public void populate(Collection<Document> documents) {
		// TODO Auto-generated method stub

		ArrayList<TermsVectorEntity> LocaltermsVectorEntities = new ArrayList<>();

		documents.stream().forEach(document -> {
			LocaltermsVectorEntities.addAll(termsVectors(document));
		});

		
		readWriteLock.writeLock().lock();
		try {

			this.termsVectorEntities = LocaltermsVectorEntities;

		} finally {
			readWriteLock.writeLock().unlock();
		}

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

		public TermsVectorEntity(TermsVector termsVector, Long documentId) {
			this.termsVector = termsVector;
			this.documentId = documentId;
		}

	}

	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

}
