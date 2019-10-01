package ztysdmy.textmining.model;

public class PredictionResult<T> {
	
	private final Target<T> target;
	private final Double probability;
	
	public PredictionResult(Target<T> target, Double probability) {
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
