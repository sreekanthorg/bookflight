package stepdefinitions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.DataObject;
import utils.FlightDetails;
import utils.PageHelper;
import utils.SortFlight;

public class FlightsListPage {
	private WebDriver driver;

	public FlightsListPage(WebDriver driver) {
		this.driver = driver;
	}

	private By form = By.xpath("(//form[@id='flightForm'])[2]");
	private By form2 = By.cssSelector(".p-relative.ow-tuple-container");
	private By price = By
			.xpath("(//ul[@class='listView flights'])[2]//tbody[contains(@class,'segment  ')]//span[@class='INR']");
	private By departureTime = By
			.xpath("(//ul[@class='listView flights'])[2]//tbody[contains(@class,'segment  ')]//th[@class='depart']");
	private By duration = By
			.xpath("(//ul[@class='listView flights'])[2]//tbody[contains(@class,'segment  ')]//th[@class='duration']");
	private By arrival = By
			.xpath("(//ul[@class='listView flights'])[2]//tbody[contains(@class,'segment  ')]//th[@class='arrive']");
	private By bookButtonUnderFormOne = By.cssSelector("td.price.actionPrice > button");			
	private By formTwoDepartureArrivalDuration = By.cssSelector(
			".p-relative.ow-tuple-container.c-pointer.hover\\:elevation-3  > div.ow-tuple-container__details.ms-grid-row-2 p");
	private By bookButtonUnderFormTwo = By
			.cssSelector(".p-relative.ow-tuple-container.c-pointer.hover\\:elevation-3  .ms-grid-column-4>button");
	private By continueButton = By.xpath("//input[@value='Continue booking']");
	private By priceDisplayed = By.xpath("//strong[@id='totalFare']//span[@id='counter']");
	boolean formOneDisplayed;
	boolean formTwoDisplayed;
	Date dept = null;
	Date arrv;
	SimpleDateFormat format = new SimpleDateFormat("HH:mm");
	List<String> bestFlights = new ArrayList<String>();
	List<FlightDetails> bestFlightsSorting = new ArrayList<FlightDetails>();
	int indexOfRowToClick = 0;
	private String parentWindow;
	DataObject dataObject = new DataObject();

	public void verifyFlightsListPageDisplayed() throws InterruptedException {
		Thread.sleep(25000);
		formOneDisplayed = driver.findElements(form).size() > 0;
		formTwoDisplayed = driver.findElements(form2).size() > 0;
		if (formOneDisplayed) {
			Assert.assertTrue(formOneDisplayed);
		} else {
			Assert.assertTrue(formTwoDisplayed);
		}
	}

	public void selectFastestAndCheapestFlight() throws ParseException {
		if (formOneDisplayed) {
			List<WebElement> prices = driver.findElements(price);
			List<WebElement> departTimes = driver.findElements(departureTime);
			List<WebElement> durationTimes = driver.findElements(duration);
			List<WebElement> arrivalTimes = driver.findElements(arrival);
			int count = driver.findElements(duration).size();
			for (int i = 0; i < count; i++) {
				try {
					getTheCheapestFlight(prices, departTimes, durationTimes, arrivalTimes, i);
				} catch (StaleElementReferenceException ex) {
					driver.navigate().refresh();
				}
			}
			bestFlightsSorting.sort(new SortFlight());
			indexOfRowToClick = getRowIndex(bestFlightsSorting);
			clickOnBookButton(indexOfRowToClick-1, bookButtonUnderFormOne);
		} else {
			String value = "";
			List<WebElement> depArrDur = driver.findElements(formTwoDepartureArrivalDuration);
			for (int i = 0, j = 1; i < depArrDur.size(); i++) {
				if (j > 6) {
					j = 1;
					value = value + "\n";
					bestFlights.add(value);
					value = "";
				}
				if (j == 1 || j == 2 || j == 3 || j == 6) {
					value += depArrDur.get(i).getText() + "\t";
					if (j == 1) {
						dept = format.parse(depArrDur.get(i).getText());
					} else if (j == 2) {
						arrv = format.parse(depArrDur.get(i).getText());
						value += ((arrv.getTime() - dept.getTime()) / 1000) / 60;
						value += "\t";
					}
				}
				j++;
			}
			indexOfRowToClick = getRowIndexForFormTwo(bestFlights);
			clickOnBookButton(indexOfRowToClick, bookButtonUnderFormTwo);
		}
	}

	private void getTheCheapestFlight(List<WebElement> prices, List<WebElement> departTimes,
			List<WebElement> durationTimes, List<WebElement> arrivalTimes, int i) throws ParseException {
		int minutes = PageHelper.convertToMinutes(durationTimes.get(i).getText());
		String text = "departTimes:" + departTimes.get(i).getText() + "\tarrival time:" + arrivalTimes.get(i).getText()
				+ "\tduration time:" + minutes + "\tPrice is:" + prices.get(i).getAttribute("data-pr");
		bestFlights.add(text);
		bestFlightsSorting.add(new FlightDetails(format.parse(arrivalTimes.get(i).getText()),
				format.parse(departTimes.get(i).getText()), minutes,
				Integer.valueOf(prices.get(i).getAttribute("data-pr").replace("₹", "").replace(",", "")), i + 1));
	}

	private void clickOnBookButton(int indexOfRowToClick, By bookButton) {
		List<WebElement> bookButtons = driver.findElements(bookButton);
		for (int i = 0; i < bookButtons.size(); i++) {
			if (i == indexOfRowToClick) {
				parentWindow = driver.getWindowHandle();
				bookButtons.get(i).click();
				break;
			}
		}
	}

	private int getRowIndexForFormTwo(List<String> flightsList) {
		int smallestDuration = Integer.MAX_VALUE;
		int rowIndex = 0;
		boolean modified = false;
		for (int i = 0; i < flightsList.size(); i++) {
			modified = false;
			String[] row = flightsList.get(i).split("\t");
			for (int j = 0; j < row.length; j++) {
				if (j == 2 && Integer.valueOf(row[j]) < smallestDuration) {
					smallestDuration = Integer.valueOf(row[j]);
					rowIndex = i;
					dataObject.setIndex(i);
					modified = true;
				} else if (j == 4 && modified) {
					dataObject.setPrice(row[j].replace("₹", "").replace(",", ""));
				}
			}
		}
		return rowIndex;
	}

	private int getRowIndex(List<FlightDetails> flightDetailList) {
		int rowIndex = 0;
		for (FlightDetails best : flightDetailList) {
			rowIndex = best.getIndexOfFlight();
			dataObject.setIndex(rowIndex);
			dataObject.setPrice(String.valueOf(best.getPrice()));
			break;
		}
		return rowIndex;
	}

	public void verifyContinuePageDisplayed() {
		Set<String> windows = driver.getWindowHandles();
		for (String window : windows) {
			if (!window.equalsIgnoreCase(parentWindow)) {
				driver.switchTo().window(window);
			}
		}
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(continueButton));		
		PageHelper.WaitForElement(driver, continueButton);		
		List<WebElement> prices = driver.findElements(priceDisplayed);
		for (WebElement price : prices) {
			if (price.isDisplayed()) {
				String priceToCheck = price.getText().replace(",", "");
				System.out.println("(dataObject.getPrice()"+(dataObject.getPrice()));
				System.out.println("priceToCheck"+priceToCheck);
				System.out.println("Asserting :: "+(dataObject.getPrice().contains(priceToCheck)));
				Assert.assertTrue(dataObject.getPrice().contains(priceToCheck));
				break;
			}
		}
		Assert.assertTrue(driver.findElement(continueButton).isDisplayed());
	}
}
