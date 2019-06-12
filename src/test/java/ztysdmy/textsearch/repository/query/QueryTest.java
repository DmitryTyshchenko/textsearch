package ztysdmy.textsearch.repository.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textsearch.model.Document;
import ztysdmy.textsearch.model.Field;
import ztysdmy.textsearch.model.TermsVectorBuilder;
import ztysdmy.textsearch.model.TextSegment;
import ztysdmy.textsearch.model.TextSegmentField;
import ztysdmy.textsearch.repository.TextRepository;
import ztysdmy.textsearh.repository.query.DistanceQuery;
import ztysdmy.textsearh.repository.query.FilterQueryDecorator;
import ztysdmy.textsearh.repository.query.GetAllQuery;

public class QueryTest {

	@Test
	public void shouldSiftAllResults() throws Exception {

		clearAndPopulateTextRepository();
		var query = new FilterQueryDecorator(new GetAllQuery(), List.of(document -> false));
		var queryResult = query.query();
		Assert.assertEquals(true, queryResult.isEmpty());
	}

	@Test
	public void shouldExecuteDistanceQuery() throws Exception {
		clearAndPopulateTextRepository();
		var query = new DistanceQuery(TermsVectorBuilder.build(TextSegment.from("test")));
		var queryResult = query.query();
		Assert.assertEquals(1, queryResult.size());
	}

	
	@Test
	public void shouldSiftAllResultsFromDistanceQuery() throws Exception {
		
		clearAndPopulateTextRepository();
		var query = new FilterQueryDecorator(new DistanceQuery(TermsVectorBuilder.build(TextSegment.from("test"))), List.of(document->false));
		var queryResult = query.query();
		Assert.assertEquals(0, queryResult.size());
	}
	
	private void clearAndPopulateTextRepository() {

		var docs = documents();
		TextRepository.instance().clear();
		TextRepository.instance().populate(docs);

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
