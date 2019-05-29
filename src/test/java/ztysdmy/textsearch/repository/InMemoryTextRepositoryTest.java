package ztysdmy.textsearch.repository;

import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textsearch.model.Document;
import ztysdmy.textsearch.model.Field;
import ztysdmy.textsearch.model.TermsVector;
import ztysdmy.textsearch.model.TermsVectorBuilder;
import ztysdmy.textsearch.model.TextSegment;
import ztysdmy.textsearch.model.TextSegmentField;

public class InMemoryTextRepositoryTest {

	
	@Test
	public void shouldGetAllDocuments() throws Exception {
		
		TextRepository textRepository = InMemoryTextRepository.instance();
		Collection<Document> documents =  documents();
		textRepository.populate(documents);
		Collection<Document>  result = textRepository.get();
		Document[] array = result.toArray(new Document[0]);
		Assert.assertEquals(2, array.length);
	}
	
	@Test
	public void shouldSortDocuments() throws Exception {
		
		TextRepository textRepository = InMemoryTextRepository.instance();
		Collection<Document> documents =  documents();
		Document[] array1 = documents.toArray(new Document[0]);
		textRepository.populate(documents);
		TermsVector request = TermsVectorBuilder.build(TextSegment.from("test1"));
		Collection<Document> documents2 = textRepository.get(request);
		Document[] array2 = documents2.toArray(new Document[0]);
		Assert.assertEquals(array2[0].identifier(), array1[1].identifier());
	}
	
	private Collection<Document> documents() {

		List<Document> result = List.of(document(TextSegment.from("test")), document(TextSegment.from("test1")));
		return result;
	}

	private Document document(TextSegment textSegment) {
		Field<TextSegment> field1 = new TextSegmentField("test", textSegment);
		Document document = new Document();
		document.addField(field1);
		return document;
	}

}
