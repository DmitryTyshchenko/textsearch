package ztysdmy.textmining.pmml;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class PMMLGenerator {
	
	private PMMLGenerator() {}

	public static String marshal(PMML pmml) {
		try {
			var context = JAXBContext.newInstance(PMML.class);
			var mar = context.createMarshaller();
			mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			var writer = new StringWriter();
			mar.marshal(pmml, writer);
			return writer.toString();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
}
