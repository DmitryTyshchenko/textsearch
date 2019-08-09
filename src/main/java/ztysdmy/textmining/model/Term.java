package ztysdmy.textmining.model;

public class Term {

	private final String value;

	public Term(String value) {
		this.value = value;
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

		if (obj instanceof Term) {

			return value.equals(((Term) obj).value());
		}

		return false;
	}
}
