package runner;

import java.util.Map;
import io.restassured.response.Response;

public class TestContext {

	private TestDataUtility testData;
	private Map<String,String> testDataMap;
	private Response response;
	
	public TestContext(){
	}
		 
	public void setTestDataObject(String excelSheetName, String excelFormat) {
	testData = new TestDataUtility(excelSheetName, excelFormat);
	}
	
	public void setTestDataObject(String fileName, String excelSheetName, String excelFormat) {
		testData = new TestDataUtility(fileName, excelSheetName, excelFormat);
	}
		
	
	public TestDataUtility getTestDataObject() {
		 return testData;
	}
		
	public void setTestData(Map<String,String> map) {
		testDataMap = map;
	}
	 
	public Map<String,String> getTestData() {
		 return testDataMap;	 
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response= response;
	}
	
}