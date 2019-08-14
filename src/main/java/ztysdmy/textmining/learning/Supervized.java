package ztysdmy.textmining.learning;

import ztysdmy.textmining.classifier.Classifier;

public interface Supervized<T> {
	Classifier<T> build();
}
