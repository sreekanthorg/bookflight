package runner;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * @author sreekanth
 *
 */
@CucumberOptions(features = { "src/test/java/features" },
glue = { "hooks",
		"stepdefinitions" }, 
dryRun = false, monochrome = true, 
plugin = { "pretty", "html:target/CucumberHtmlReport" })

public class TestNgRunner extends AbstractTestNGCucumberTests {
	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		return super.scenarios();
	}
}
