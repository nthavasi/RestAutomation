package runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.*;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="src/test/java/featureFiles/",
		glue="stepDefinitions",
		plugin={
				"pretty", "html:TestResults/html",
				"json:TestResults/cucumber.json" ,
				"junit:TestResults/cucumber.xml",
		},monochrome = true
		)

public class TestRunner {
	
}