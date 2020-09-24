package ztysdmy.textmining.learning;

public class EpochesStopCriteria implements LearningStopCriteria {

	private int epoches;

	public EpochesStopCriteria(int epoches) {
		this.epoches = epoches;
	}

	public EpochesStopCriteria() {
		this(10000);
	}

	@Override
	public void whileStopCriteriaHasNotReached(Runnable action) {
		for (int i = 0; i < epoches; i++) {
			action.run();
		}
	}
}
