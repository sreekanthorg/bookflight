package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import stepdefinitions.FlightsListPage;
import utils.PageHelper;

public class FlightBookingPage {
	private WebDriver driver;

	private By imageDisplayed = By.xpath("//span[@class='cleartripLogo']");
	private By fromTextbox = By.id("FromTag");
	private By destinationTextbox = By.id("ToTag");
	private By departureDate = By.xpath("//input[@id='DepartDate']");
	private By searchButton = By.xpath("//input[@id='SearchBtn']");

	public FlightBookingPage(WebDriver driver) {
		this.driver = driver;
	}

	public void verifyFlightBookingPageIsDisplayed() {
		Assert.assertTrue(driver.findElement(imageDisplayed).isDisplayed());
	}

	public void enterValuesToSourceAndDestination(String source, String destination) {
		driver.findElement(fromTextbox).sendKeys(source);
		WebElement destinationElement = PageHelper.WaitForElement(driver, destinationTextbox);
		destinationElement.sendKeys(destination);
	}

	public void enterDepartureDate() {
		WebElement dateToEnter = PageHelper.WaitForElement(driver, departureDate);
		dateToEnter.clear();
		dateToEnter.sendKeys(PageHelper.getNextDate());
		dateToEnter.sendKeys(Keys.TAB);
	}

	public FlightsListPage clickOnSearchButton() {
		WebElement searchButtonToClick = PageHelper.WaitForElement(driver, searchButton);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", searchButtonToClick);
		searchButtonToClick.click();
		return new FlightsListPage(driver);
	}

}
