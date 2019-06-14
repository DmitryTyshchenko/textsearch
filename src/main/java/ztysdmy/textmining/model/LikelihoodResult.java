package ztysdmy.textmining.model;

public class LikelihoodResult {
	
	private final Fact document;
	private final Double probability;
	
	public LikelihoodResult(Fact document, Double probability) {
		this.document = document;
		this.probability = probability;
	}

	public Fact document() {
		return this.document;
	}
	
	public Double probability() {
		return this.probability;
	}
}
