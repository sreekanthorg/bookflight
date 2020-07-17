package stepdefinitions;

import java.text.ParseException;

import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.FlightBookingPage;
import utils.DriverManager;

public class BookAFlight {

	private DriverManager driverManager;
	private WebDriver driver;
	private FlightBookingPage flightBookingPage;
	private FlightsListPage flightsListPage;

	public BookAFlight(DriverManager driverManager) {
		this.driverManager = driverManager;
		this.driver = driverManager.getDriver();
		flightBookingPage = new FlightBookingPage(driver);
	}

	@Given("flight booking page is displayed")
	public void flight_booking_page_is_displayed() {
		flightBookingPage.verifyFlightBookingPageIsDisplayed();
	}

	@When("I enter source as {string} and destination as {string}")
	public void i_enter_source_as_and_destination_as(String source, String destination) {
		flightBookingPage.enterValuesToSourceAndDestination(source, destination);
	}

	@When("I enter departure date and click on search button")
	public void i_enter_departure_date_and_click_on_search_button() {
		flightBookingPage.enterDepartureDate();
		flightsListPage = flightBookingPage.clickOnSearchButton();
	}


	@Then("I should see the flights list page")
	public void i_should_see_the_flights_list_page() throws InterruptedException {
		flightsListPage.verifyFlightsListPageDisplayed();
	}

	@When("I click on book against the best flight")
	public void i_click_on_book_against_the_best_flight() throws ParseException {
		flightsListPage.selectFastestAndCheapestFlight();
	}

	@Then("I should i see fare summary page")
	public void i_should_i_see_fare_summary_page() {
		flightsListPage.verifyContinuePageDisplayed();
	}

}
