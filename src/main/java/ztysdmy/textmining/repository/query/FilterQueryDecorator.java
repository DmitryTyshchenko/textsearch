package ztysdmy.textmining.repository.query;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import ztysdmy.textmining.model.Fact;

public class FilterQueryDecorator extends QueryDecorator {

	private Predicate<Fact> predicate;

	public FilterQueryDecorator(Query query, List<Predicate<Fact>> predicates) {
		super(query);
		this.predicate = toPredicate(predicates);
	}

	@Override
	public Collection<Fact> query() {
		return super.query().stream().filter(predicate).collect(Collectors.toList());
	}

	private Predicate<Fact> toPredicate(List<Predicate<Fact>> predicates) {

		Predicate<Fact> identity = document -> true;

		for (Predicate<Fact> predicate : predicates) {

			identity = identity.and(predicate);
		}

		return identity;
	}
}
