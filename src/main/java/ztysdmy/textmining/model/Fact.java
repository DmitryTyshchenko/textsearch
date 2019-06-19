package ztysdmy.textmining.model;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class Fact<T> implements Identifierable<Long> {

	private static final AtomicLong identifierGenerator = new AtomicLong();

	private final Long identifier;

	private Target<T> target;

	private String value;

	public Fact(String value, Target<T> target) {
		this(value);
		this.target = target;
	}

	public Fact(String value) {
		this.value = value;
		identifier = identifierGenerator.incrementAndGet();
	}

	public String value() {
		return value;
	}

	public Optional<Target<T>> target() {
		return Optional.ofNullable(target);
	}

	public void setTarget(Target<T> target) {
		this.target = target;
	}

	@Override
	public Long identifier() {
		return identifier;
	}

}
