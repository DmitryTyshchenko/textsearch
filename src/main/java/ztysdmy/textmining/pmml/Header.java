package ztysdmy.textmining.pmml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "copyright", "description" })
public class Header {

	public Header() {}
	
	public Header(String description, String copyright) {
		this.description = description;
		this.copyright = copyright;
	}
	
	@XmlAttribute
	private String description;
	
	@XmlAttribute
	private String copyright;
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
}
