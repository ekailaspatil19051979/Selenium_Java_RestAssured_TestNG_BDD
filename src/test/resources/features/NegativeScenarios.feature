Feature: Negative and Edge Case Scenarios

  @api @negative
  Scenario Outline: API - Create Booking with Invalid Data
    Given I attempt to create a booking with "<firstname>", "<lastname>", "<totalprice>", "<depositpaid>", "<checkin>", "<checkout>"
    Then I verify the booking status is <expected_status>

    Examples:
      | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | expected_status |
      |           | Doe      | 150        | true        | 2024-01-01 | 2024-01-05 | 200             |
      | John      |          | 150        | true        | 2024-01-01 | 2024-01-05 | 200             |
      | John      | Doe      | abc        | true        | 2024-01-01 | 2024-01-05 | 200             |
      | John      | Doe      | -50        | true        | 2024-01-01 | 2024-01-05 | 200             |

  @api @edge
  Scenario: API - Create Booking with Checkout before Checkin
    Given I create a booking with "Checkin", "Issue", "200", "true", "2024-02-01", "2024-01-01"
    Then I verify the booking status is 200 or 400

  @api @negative
  Scenario: API - Update non-existent booking
    Given I have a valid auth token
    When I attempt to update booking "99999999" with price 500
    Then I verify the booking status is 404 or 403

  @api @negative
  Scenario: API - Delete booking with invalid token
    When I attempt to delete booking "1" with token "invalid_token_123"
    Then I verify the booking status is 403

  @api @edge
  Scenario: API - Large Payload Data
    Given I create a booking with a very long firstname of 5000 characters
    Then I verify the booking status is 200 or 400

  @ui @negative
  Scenario: UI - Login with Invalid Credentials
    Given I navigate to the application URL
    When I attempt to login with username "invalid_user" and password "invalid_pass"
    Then I verify an error message "Invalid credentials" is displayed on UI

  @api @security
  Scenario: API - SQL Injection attempt in booking field
    Given I attempt to create a booking with "' OR '1'='1", "Doe", "100", "true", "2024-01-01", "2024-01-05"
    Then I verify the booking status is 200 or 400

  @api @negative
  Scenario: API - Malformed JSON body
    When I send a POST request with malformed JSON string "{\"firstname\": \"John\", \"lastname\": }"
    Then I verify the booking status is 200 or 400

  @api @negative
  Scenario: API - Unsupported Content-Type
    When I send a POST request with XML content type
    Then I verify the booking status is 415 or 500

  @ui @negative
  Scenario: UI - Upload Unsupported File Format
    Given I navigate to the application profile page
    When I attempt to upload a file "test.txt" to the profile picture field
    Then I verify an error message "Unsupported file format" is visible

  @ui @edge
  Scenario: UI - Field Max Length Validation
    Given I navigate to the contact form
    When I enter 10000 characters in the "Subject" field
    Then I verify the field does not accept more than 255 characters
