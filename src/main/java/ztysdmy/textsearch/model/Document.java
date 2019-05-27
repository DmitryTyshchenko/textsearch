package ztysdmy.textsearch.model;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Document implements Identifierable<Long> {

	private static final AtomicLong identifierGenerator = new AtomicLong();

	private HashMap<String, Field<?>> fields = new HashMap<>();

	@Override
	public Long identifier() {
		return identifierGenerator.incrementAndGet();
	}

	public void addField(Field<?> field) {
		fields.put(field.name(), field);
	}

	
	public List<TextProvider> textProviders() {

		return fields.entrySet().stream()
				.filter(entry -> TextProvider.class.isAssignableFrom(entry.getValue().value().getClass()))
				.map(entry -> (TextProvider) entry.getValue().value()).collect(Collectors.toList());
	}
	
}
