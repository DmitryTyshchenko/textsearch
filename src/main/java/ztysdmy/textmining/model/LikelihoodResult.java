package ztysdmy.textmining.model;

public class LikelihoodResult<T> {
	
	private final Target<T> target;
	private final Double probability;
	
	public LikelihoodResult(Target<T> target, Double probability) {
		this.target = target;
		this.probability = probability;
	}

	public Target<T> target() {
		return this.target;
	}
	
	public Double probability() {
		return this.probability;
	}
}
