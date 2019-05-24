package ztysdmy.textsearch.model;

public class Field<T> {

	private T value;

	private String name;

	public Field(String name, T value) {
		this.name = name;
		this.value = value;
	}

	public String name() {
		return name;
	}

	public T value() {
		return value;
	}
}
