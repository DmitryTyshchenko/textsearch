package ztysdmy.textsearch.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Term {

	private final String value;

	private final AtomicInteger occurances = new AtomicInteger(1);

	public Term(String value) {
		this.value = value;
	}

	public void increment() {
		occurances.incrementAndGet();
	}

	public int occurances() {
		return occurances.intValue();
	}

	public String value() {
		return this.value;
	}
}
