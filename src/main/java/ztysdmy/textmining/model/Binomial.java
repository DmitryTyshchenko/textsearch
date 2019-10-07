package ztysdmy.textmining.model;

public enum Binomial {

	YES(1.0d), NO(0.0d);

	private Binomial(double value) {
		this.value = value;
	}

	private double value;

	public double value() {
		return value;
	}

}
