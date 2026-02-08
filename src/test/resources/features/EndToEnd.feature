Feature: End to End Booking Management

  @api @ui
  Scenario Outline: Create a booking via API and verify on UI
    Given I create a booking with "<firstname>", "<lastname>", "<totalprice>", "<depositpaid>", "<checkin>", "<checkout>"
    When I verify the booking status is 200
    Then I navigate to the application URL
    And I verify the booking is displayed on the UI

    Examples: 
      | firstname | lastname | totalprice | depositpaid | checkin    | checkout   |
      | John      | Doe      | 150        | true        | 2024-01-01 | 2024-01-05 |

  @api
  Scenario: Update booking via API
    Given I have a valid auth token
    When I update the existing booking with price 200
    Then I verify the booking price is updated to 200

  @api
  Scenario: Delete booking via API
    Given I have a valid auth token
    When I delete the existing booking
    Then I verify the booking status is 201
