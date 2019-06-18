package ztysdmy.textmining.model;

import java.util.concurrent.atomic.AtomicLong;

public class Fact<T> implements Identifierable<Long> {

	private static final AtomicLong identifierGenerator = new AtomicLong();

	private final Long identifier;
	
	private Target<T> target;
	
	private String value;
	
	public Fact(String value, Target<T> target) {
		
		this.value = value;
		identifier = identifierGenerator.incrementAndGet();
	}
	
	public String value() {
		return value;
	}
	
	public Target<T> target() {
		return target;
	}
	
	@Override
	public Long identifier() {
		return identifier;
	}

	
}
