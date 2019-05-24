package ztysdmy.textsearch.model;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Document implements Identifierable {

	private static final AtomicLong identifierGenerator = new AtomicLong();

	private HashMap<String, Field<?>> fields = new HashMap<>();

	@Override
	public Long identifier() {
		return identifierGenerator.incrementAndGet();
	}

	public void addField(Field<?> field) {
		fields.put(field.name(), field);
	}

}
