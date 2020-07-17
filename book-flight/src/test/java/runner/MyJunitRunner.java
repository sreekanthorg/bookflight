package runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = { "src/test/java/features" },
		glue = { "stepdefinitions","hooks" },
		dryRun = false, monochrome = true,
		plugin = {
		"pretty", "html:reports/index.html" })
public class MyJunitRunner {

}
