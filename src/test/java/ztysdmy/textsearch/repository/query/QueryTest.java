package ztysdmy.textsearch.repository.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textsearch.model.Document;
import ztysdmy.textsearch.model.Field;
import ztysdmy.textsearch.model.TextSegment;
import ztysdmy.textsearch.model.TextSegmentField;
import ztysdmy.textsearch.repository.TextRepository;
import ztysdmy.textsearh.repository.query.FilterQueryDecorator;
import ztysdmy.textsearh.repository.query.GetAllQuery;

public class QueryTest {

	@Test
	public void shouldSiftAllResults() throws Exception {
		
		var docs = documents();
		TextRepository.instance().populate(docs);
		var query = new FilterQueryDecorator(new GetAllQuery(), List.of(document->false));
		var queryResult = query.query();
		Assert.assertEquals(true, queryResult.isEmpty());
	}
	
	
	private Document document() {

		Field<TextSegment> field1 = new TextSegmentField("test", TextSegment.from("test"));
		Field<Long> field2 = new Field<>("test2", 1l);
		Document document = new Document();
		document.addField(field1);
		document.addField(field2);
		return document;
	}
	
	private Collection<Document> documents() {
		Collection<Document> result = new ArrayList<>();
		result.add(document());
		return result;
	}
}
