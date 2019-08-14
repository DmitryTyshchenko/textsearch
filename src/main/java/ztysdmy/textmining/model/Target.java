package ztysdmy.textmining.model;

public class Target<T> {

	private T value;

	public Target(T value) {
		this.value = value;
	}

	public T value() {
		return this.value;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object object) {
		if (object instanceof Target) {
			return this.value.equals(((Target) object).value());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.value.hashCode();
	}
}
