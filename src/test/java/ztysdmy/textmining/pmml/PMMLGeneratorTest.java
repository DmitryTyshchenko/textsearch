package ztysdmy.textmining.pmml;

import static ztysdmy.textmining.pmml.PMMLGenerator.marshal;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;



public class PMMLGeneratorTest {

	@Test
	public void shoulMarchalToString() throws Exception {
		
		var builder = new PMML.Builder();
		var header = new Header("Test", "Test");
		//var pmml = builder.setHeader(header).setDataDictionary(new DataDictionary()).build();
		//System.out.println(marshal(pmml));
	}
	
}
