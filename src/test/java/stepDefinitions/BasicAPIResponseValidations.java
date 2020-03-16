package stepDefinitions;

import java.io.File;
import java.io.IOException;
import org.junit.Assert;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import runner.TestContext;

public class BasicAPIResponseValidations {

	TestContext testContext;

	public BasicAPIResponseValidations (TestContext context) {
		testContext = context;	 
	}

	@Given("Read test Data from Excel for {string} and {string}")
	public  void read_test_Data_from_Excel_for(String testScenarioName, String testCaseName) throws IOException {
		testContext.setTestDataObject("Sheet1", "xlsx");
		testContext.setTestData(testContext.getTestDataObject().readExcel(testScenarioName, testCaseName));
	}
	
	@When("Request is sent and the Response is generated")
	public void Request_is_sent_and_the_Response_is_generated() {
		System.out.println("Req Type1"+testContext.getTestData().get("RequestType1").toLowerCase());

		if(testContext.getTestData().get("RequestType1").toLowerCase().contentEquals("get")||testContext.getTestData().get("RequestType1").toLowerCase().contentEquals("delete")){
		
		//Create Objects to create API Request
		RestAssured.baseURI =testContext.getTestData().get("BASEURL");
		RequestSpecification request = RestAssured.given();
		
		//Send request and store response
		Response response = request.put(testContext.getTestData().get("Resource"));
		
		//Set response to TestContext for use in another class
		testContext.setResponse(response);
		}
		
		if(testContext.getTestData().get("RequestType1").toLowerCase().contentEquals("put")||testContext.getTestData().get("RequestType1").toLowerCase().contentEquals("post")){
			
			//Create Objects to create API Request
			RestAssured.baseURI =testContext.getTestData().get("BASEURL");
			RequestSpecification request = RestAssured.given();
			String bodyFilePath = System.getProperty(("user.dir"))+"//src//test//resources//requests//"+testContext.getTestData().get("RequestBody1");
			request.body(new File(bodyFilePath));

			//Send request and store response
			Response response = request.put(testContext.getTestData().get("Resource"));
			
			//Set response to TestContext for use in another class
			testContext.setResponse(response);
			
			}
	}

	@And("Validate the response status code")
	public void Validate_the_response_status_code() {
		
		//Extract response from object in TestContext and validate against expected status codes
		System.out.println("Response"+testContext.getResponse().statusCode());
		Assert.assertEquals(testContext.getResponse().statusCode(), Integer.parseInt(testContext.getTestData().get("StatusCode1")));
	}
	
}