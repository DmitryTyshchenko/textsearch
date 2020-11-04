package ztysdmy.textmining.pmml;

public interface PMMLExportable {

	DataDictionary dataDictionary();
	
	RegressionModel regressionModel(String modelName);
}
