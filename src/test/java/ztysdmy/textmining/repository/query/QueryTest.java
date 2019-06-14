package ztysdmy.textmining.repository.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ztysdmy.textmining.model.Fact;
import ztysdmy.textmining.model.Field;
import ztysdmy.textmining.model.Text;
import ztysdmy.textmining.model.TextField;
import ztysdmy.textmining.repository.FactRepository;
import ztysdmy.textmining.repository.query.FilterQueryDecorator;
import ztysdmy.textmining.repository.query.GetAllFactsQuery;

public class QueryTest {

	@Test
	public void shouldSiftAllResults() throws Exception {

		clearAndPopulateTextRepository();
		var query = new FilterQueryDecorator(new GetAllFactsQuery(), List.of(document -> false));
		var queryResult = query.query();
		Assert.assertEquals(true, queryResult.isEmpty());
	}
	
	
	private void clearAndPopulateTextRepository() {

		var docs = documents();
		FactRepository.instance().clear();
		FactRepository.instance().populate(docs);

	}

	private Fact document() {

		Field<Text> field1 = new TextField("test", Text.from("test"));
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
