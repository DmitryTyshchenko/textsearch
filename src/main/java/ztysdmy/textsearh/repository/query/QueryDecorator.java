package ztysdmy.textsearh.repository.query;

import java.util.Collection;

import ztysdmy.textsearch.model.Fact;

public abstract class QueryDecorator implements Query {

	private Query query;

	public QueryDecorator(Query query) {
		
		this.query = query;
	}
	
	@Override
	public Collection<Fact> query() {
		return query.query();
	}
}
