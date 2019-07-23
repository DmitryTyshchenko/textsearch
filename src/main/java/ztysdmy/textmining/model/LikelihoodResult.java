package ztysdmy.textmining.model;

public class LikelihoodResult<T> {
	
	private final Fact<T> fact;
	private final Double probability;
	
	public LikelihoodResult(Fact<T> fact, Double probability) {
		this.fact = fact;
		this.probability = probability;
	}

	public Fact<T> fact() {
		return this.fact;
	}
	
	public Double probability() {
		return this.probability;
	}
}
