package ztysdmy.textsearh.repository.query;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import ztysdmy.textsearch.model.Document;

public class FilterQueryDecorator extends QueryDecorator {

	private Predicate<Document> predicate;

	public FilterQueryDecorator(Query query, List<Predicate<Document>> predicates) {
		super(query);
		this.predicate = toPredicate(predicates);
	}

	@Override
	public Collection<Document> query() {
		return super.query().stream().filter(predicate).collect(Collectors.toList());
	}

	private Predicate<Document> toPredicate(List<Predicate<Document>> predicates) {

		Predicate<Document> identity = document -> true;

		for (Predicate<Document> predicate : predicates) {

			identity = identity.and(predicate);
		}

		return identity;
	}
}
