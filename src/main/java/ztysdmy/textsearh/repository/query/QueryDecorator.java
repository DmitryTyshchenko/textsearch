package ztysdmy.textsearh.repository.query;

import java.util.Collection;

import ztysdmy.textsearch.model.Document;

public abstract class QueryDecorator implements Query {

	private Query query;

	public QueryDecorator(Query query) {
		
		this.query = query;
	}
	
	@Override
	public Collection<Document> query() {
		return query.query();
	}
}
