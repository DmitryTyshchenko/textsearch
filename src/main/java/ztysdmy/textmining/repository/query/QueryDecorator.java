package ztysdmy.textmining.repository.query;

import java.util.Collection;

import ztysdmy.textmining.model.Fact;

public abstract class QueryDecorator<T> implements Query<T> {

	private Query<T> query;

	public QueryDecorator(Query<T> query) {
		
		this.query = query;
	}
	
	@Override
	public Collection<Fact<T>> query() {
		return query.query();
	}
}
