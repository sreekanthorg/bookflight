Feature: Flight Booking

  Scenario Outline: : Book a Flight
    Given flight booking page is displayed
    When I enter source as "<source>" and destination as "<destination>"
    And I enter departure date and click on search button
    Then I should see the flights list page
    When I click on book against the best flight
    Then I should i see fare summary page

    Examples: 
      | source    | destination |
      | Mumbai    | Delhi       |
      | Hyderabad | Chennai     |
      | Bangalore | Delhi       |
      | Delhi     | Chennai     |
