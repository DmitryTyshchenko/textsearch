package ztysdmy.textmining.pmml;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class DataDictionary {

	public DataDictionary(int numberOfFields, List<DataField> dataFields) {
		this.numberOfFields = numberOfFields;
		this.dataFields = dataFields;
	}

	@XmlAttribute
	private final int numberOfFields;

	@XmlElement(name = "DataField")
	private final List<DataField> dataFields;

	public static class DataField {

		public DataField(String name, Value value) {
			this.name = name;
			this.value = value;
		}

		@XmlAttribute
		private final String name;
		@XmlAttribute
		private OPTYPE optype = OPTYPE.categorical;
		@XmlAttribute
		private DATATYPE dataType = DATATYPE.string;

		@XmlElement(name = "Value")
		private final Value value;

		/**
		 * pmml standart states that DataField should have a name. However for
		 * textmining name can coincide with the value.
		 */
		public static DataField fromValue(String value) {
			return new DataField(value, new Value(value));
		}

	}

	public static enum OPTYPE {
		categorical, ordinal, continuous;
	}

	public static enum DATATYPE {
		string
	}

	public static class Value {

		public Value(String value) {
			this.value = value;
		}

		@XmlAttribute
		private String value;
	}
}
