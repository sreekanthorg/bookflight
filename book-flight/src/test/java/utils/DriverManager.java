package utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverManager {
	private WebDriver driver;

	public WebDriver getDriver() {
		return driver;
	}

	public DriverManager() {
		invokeBrowser();
	}

	public void invokeBrowser() {
		String path = System.getProperty("user.dir");
		String browser = ConfigurationReader.get("browser");
		switch (browser) {
		case "chrome":
			if (System.getProperty("os.name").toLowerCase().contains("windows")) {
				System.setProperty("webdriver.chrome.driver",
						path + "/src/test/java/resources/windows/chromedriver.exe");
			} else {
				System.setProperty("webdriver.chrome.driver", path + "/src/test/java/resources/Linux/chromedriver");
			}
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);
			driver.get(ConfigurationReader.get("url"));
			break;
		case "firefox":
			if (System.getProperty("os.name").toLowerCase().contains("windows")) {
				System.setProperty("webdriver.gecko.driver", path + "/src/test/java/resources/windows/geckodriver.exe");
			} else {
				System.setProperty("webdriver.gecko.driver", path + "/src/test/java/resources/Linux/geckodriver");
			}
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);
			driver.get(ConfigurationReader.get("url"));
			break;
		default:
			break;
		}
	}
}
