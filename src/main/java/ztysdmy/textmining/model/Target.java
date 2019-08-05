package ztysdmy.textmining.model;

public class Target<T> {

	private T value;
	
	public Target(T value) {
		this.value = value;
	}
	
	public T value() {
		return this.value;
	}
	
	@Override
	public boolean equals(Object object) {
		
		return this.value.equals(object);
	}
	
	@Override
	public int hashCode() {
		return this.value.hashCode();
	}
}
