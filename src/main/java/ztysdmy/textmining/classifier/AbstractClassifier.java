package ztysdmy.textmining.classifier;

public abstract class AbstractClassifier<T> implements Classifier<T> {

	private int complexity;

	public AbstractClassifier(int c) {
		this.complexity = c;
	}
	
	protected int getComplexity() {
		return complexity;
	}
}
