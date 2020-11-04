package ztysdmy.textmining.pmml;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class RegressionModel {

	@XmlAttribute
	private String modelName;

	@XmlAttribute
	private MININGFUNCTION miningFunction = MININGFUNCTION.classification;

	public RegressionModel(String modelName, MiningSchema miningSchema, RegressionTable regressionTable) {
		this.modelName = modelName;
		this.miningSchema = miningSchema;
		this.regressionTable = regressionTable;
	}

	@XmlElement(name = "MiningSchema")
	MiningSchema miningSchema;

	@XmlElement(name = "RegressionTable")
	RegressionTable regressionTable;

	public static enum MININGFUNCTION {
		associationRules, sequences, classification, regression, clustering, timeSeries, mixed
	}

	public static record MiningSchema(@XmlElement(name="MiningField")List<MiningField> miningFields) {
	}

	public static record MiningField(@XmlAttribute String name) {

	}

	public record RegressionTable(@XmlAttribute double intercept,
			@XmlElement(name="CategoricalPredictor")List<CategoricalPredictor> predictors) {

	}

	public static record CategoricalPredictor(@XmlAttribute String name, @XmlAttribute String value,
			@XmlAttribute double coefficient) {
	};

}
