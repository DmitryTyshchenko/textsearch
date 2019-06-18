package ztysdmy.textmining.model;

public class LikelihoodResult<T> {
	
	private final Fact<T> fact;
	private final Double probability;
	
	public LikelihoodResult(Fact<T> document, Double probability) {
		this.fact = document;
		this.probability = probability;
	}

	public Fact<T> fact() {
		return this.fact;
	}
	
	public Double probability() {
		return this.probability;
	}
}
