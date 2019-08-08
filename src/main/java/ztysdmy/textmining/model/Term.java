package ztysdmy.textmining.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Term {

	private final String value;

	@Deprecated
	private final AtomicInteger occurances = new AtomicInteger(1);

	public Term(String value) {
		this.value = value;
	}

	@Deprecated
	public void increment() {
		occurances.incrementAndGet();
	}

	public int occurances() {
		return occurances.intValue();
	}

	public String value() {
		return this.value;
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return value.equals(obj);
	}
}
