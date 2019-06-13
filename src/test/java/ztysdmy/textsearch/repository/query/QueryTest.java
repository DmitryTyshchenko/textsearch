package ztysdmy.textsearch.repository.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textsearch.model.Fact;
import ztysdmy.textsearch.model.Field;
import ztysdmy.textsearch.model.TextSegment;
import ztysdmy.textsearch.model.TextSegmentField;
import ztysdmy.textsearch.repository.FactRepository;
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
	
	
	private void clearAndPopulateTextRepository() {

		var docs = documents();
		FactRepository.instance().clear();
		FactRepository.instance().populate(docs);

	}

	private Fact document() {

		Field<TextSegment> field1 = new TextSegmentField("test", TextSegment.from("test"));
		Field<Long> field2 = new Field<>("test2", 1l);
		Fact document = new Fact();
		document.addField(field1);
		document.addField(field2);
		return document;
	}

	private Collection<Fact> documents() {
		Collection<Fact> result = new ArrayList<>();
		result.add(document());
		return result;
	}
}
