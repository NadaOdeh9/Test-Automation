package runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",  // Path to the feature files
    glue = "stepdefinitions",                  // Path to the step definitions
    plugin = {"pretty",                        // Console output for Cucumber test results
              "html:target/cucumber-reports.html"},  // HTML report will be generated at target/cucumber-reports.html
    monochrome = true                         
)
public class TestRunner {

}