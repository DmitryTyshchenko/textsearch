package ztysdmy.textmining.classifier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ztysdmy.textmining.model.Term;
import ztysdmy.textmining.pmml.DataDictionary;
import ztysdmy.textmining.pmml.DataDictionary.DataField;
import ztysdmy.textmining.pmml.RegressionModel;
import ztysdmy.textmining.pmml.RegressionModel.CategoricalPredictor;
import ztysdmy.textmining.pmml.RegressionModel.MiningField;
import ztysdmy.textmining.pmml.RegressionModel.MiningSchema;
import ztysdmy.textmining.pmml.RegressionModel.RegressionTable;

class LogisticRegressionToPMMLUtility {

	private LogisticRegressionToPMMLUtility() {
	}

	static DataDictionary dataDictionary(LogisticRegression logisticRegression) {
		var dataFields = dataFields(logisticRegression);
		return new DataDictionary(dataFields.size(), dataFields);
	}

	private static List<DataField> dataFields(LogisticRegression logisticRegression) {

		return logisticRegression.POLYNOMIAL.keySet().stream().map(Term::value).map(DataField::fromValue)
				.collect(Collectors.toList());
	}

	static RegressionModel regressionModel(String modelName, LogisticRegression logisticRegression) {
		var fieldsAndPredictors = miningFieldsAndCategoricalPredictors(logisticRegression);
		return new RegressionModel(modelName, new MiningSchema(fieldsAndPredictors.miningFields()),
				new RegressionTable(logisticRegression.IDENTITY.weight(), fieldsAndPredictors.categoricalPredictors()));
	}

	private static MiningAndCategoricalFields miningFieldsAndCategoricalPredictors(
			LogisticRegression logisticRegression) {

		var fields = new ArrayList<MiningField>();
		var predictors = new ArrayList<CategoricalPredictor>();

		logisticRegression.POLYNOMIAL.forEach((term, monomial) -> {
			fields.add(new MiningField(term.value()));
			predictors.add(new CategoricalPredictor(term.value(), term.value(), monomial.weight()));
		});

		return new MiningAndCategoricalFields(fields, predictors);
	}

	private static record MiningAndCategoricalFields(List<MiningField> miningFields,
			List<CategoricalPredictor> categoricalPredictors) {

	}
}
