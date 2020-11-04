package ztysdmy.textmining.pmml;

public interface PMMLExportable {

	String toPMML(String description, String copyright, String modelName);
}
