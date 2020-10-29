package ztysdmy.textmining.pmml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "PMML")
@XmlType
public class PMML {

	@XmlAttribute
	private String version = "4.1";

	@XmlAttribute
	private String xmlns = "http://www.dmg.org/PMML-4_1";

	@XmlElement(name = "Header")
	private Header header;

	@XmlElement(name = "DataDictionary")
	private DataDictionary dataDictionary;
	
	public void setVersion(String version) {
		this.version = version;
	}

	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public void setDataDictionary(DataDictionary dataDictionary) {
		this.dataDictionary = dataDictionary;
	}
	
	public static class Builder {

		private Header header;
		private String version;
		private String xmlns;

		private DataDictionary dataDictionary;

		public Builder setHeader(Header header) {
			this.header = header;
			return this;
		}

		public Builder setVersion(String version) {
			this.version = version;
			return this;
		}

		public Builder setXmlns(String xmlns) {
			this.xmlns = xmlns;
			return this;
		}

		public Builder setDataDictionary(DataDictionary dataDictionary) {
			this.dataDictionary = dataDictionary;
			return this;
		}

		public PMML build() {
			var pmml = new PMML();
			pmml.setHeader(this.header);
			pmml.setVersion(this.version);
			pmml.setXmlns(this.xmlns);
			pmml.setDataDictionary(this.dataDictionary);
			return pmml;
		}
	}
}
