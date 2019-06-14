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

	
	public List<TextSegmentField> textSegmentFields() {

		return fields.entrySet().stream()
				.filter(entry -> TextSegmentField.class.isAssignableFrom(entry.getValue().getClass()))
				.map(entry -> (TextSegmentField) entry.getValue()).collect(Collectors.toList());
	}
	
}
