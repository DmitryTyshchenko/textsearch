package ztysdmy.textsearch.model;

import java.util.concurrent.atomic.AtomicLong;

public class Segment implements Identifierable<Long>, TextProvider {

	private String value;

	private static final AtomicLong identifierGenerator = new AtomicLong();

	private final Long identifier;

	public Segment(String value) {
		this.value = value;
		identifier = identifierGenerator.incrementAndGet();
	}

	// Utility methods to create Text Segments
	public static Segment from(String value) {

		Segment result = new Segment(value);
		return result;
	}

	public String toString() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public Long identifier() {

		return this.identifier;
	}

	@Override
	public String text() {
		return toString();
	}

}
