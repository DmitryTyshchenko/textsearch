package ztysdmy.textmining.model;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Fact implements Identifierable<Long> {

	private static final AtomicLong identifierGenerator = new AtomicLong();

	private HashMap<String, Field<?>> fields = new HashMap<>();

	private final Long identifier = identifierGenerator.incrementAndGet();
	
	
	@Override
	public Long identifier() {
		return identifier;
	}

	public void addField(Field<?> field) {
		fields.put(field.name(), field);
	}

	
	public List<TextField> textFields() {

		return fields.entrySet().stream()
				.filter(entry -> TextField.class.isAssignableFrom(entry.getValue().getClass()))
				.map(entry -> (TextField) entry.getValue()).collect(Collectors.toList());
	}
	
}
