package hooks;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.DriverManager;

public class CucumberHooks {
	private DriverManager driverManager;
	private WebDriver driver;

	public CucumberHooks(DriverManager driverManager) {
		this.driverManager = driverManager;
		this.driver = driverManager.getDriver();
	}

	@Before
	public void setUp(Scenario scenario) {
		driver.manage().deleteAllCookies();
	}

	@After
	public void tearDown(Scenario scenario) {
		if (scenario.isFailed()) {
			scenario.log("Current Page URL is: " + driver.getCurrentUrl());
			try {
				byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
				scenario.attach(screenshot, "image/png", scenario.getName());
			} catch (WebDriverException ex) {
				ex.getMessage();
			}			
		}
		if (driver != null) {
			driver.quit();
		}
	}
}
