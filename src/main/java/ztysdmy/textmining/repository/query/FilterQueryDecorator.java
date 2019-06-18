package ztysdmy.textmining.repository.query;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import ztysdmy.textmining.model.Fact;

public class FilterQueryDecorator<T> extends QueryDecorator<T> {

	private Predicate<Fact<T>> predicate;

	public FilterQueryDecorator(Query<T> query, List<Predicate<Fact<T>>> predicates) {
		super(query);
		this.predicate = toPredicate(predicates);
	}

	@Override
	public Collection<Fact<T>> query() {
		return super.query().stream().filter(predicate).collect(Collectors.toList());
	}

	private Predicate<Fact<T>> toPredicate(List<Predicate<Fact<T>>> predicates) {

		Predicate<Fact<T>> identity = document -> true;

		for (Predicate<Fact<T>> predicate : predicates) {

			identity = identity.and(predicate);
		}
		return identity;
	}
}
