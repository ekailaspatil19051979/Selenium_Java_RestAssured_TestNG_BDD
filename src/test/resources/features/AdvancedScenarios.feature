Feature: Advanced Automation Scenarios

  @api @complex
  Scenario: API Chaining - Lifecycle of a Booking
    Given I create a new booking using random data
    Then I verify the booking response matches "schemas/booking-schema.json"
    And I extract the "bookingid" from the response
    When I retrieve the details of the created booking
    Then I verify the firstname matches the previously created record
    When I update the lastname to "Smith-Updated" for this booking
    Then I verify the lastname is updated in the system
    When I delete this booking
    Then I verify the booking no longer exists

  @ui @complex
  Scenario Outline: UI Bulk Data Validation across Roles
    Given I login with role "<role>"
    Then I verify the dashboard elements corresponding to "<role>" are visible
    And I verify the specialized "<widget>" is present on the sidebar
    When I perform a complex "Multi-Filter" search for transactions
    Then I verify the search results are greater than 0
    And I logout from the application

    Examples:
      | role  | widget          |
      | Admin | User Analytics  |
      | Manager | Revenue Chart |

  @api @security
  Scenario: Security - Unauthorized Access Prevention
    Given I attempt to delete booking "9999" without an auth token
    Then I verify the status code is 403 or 401
    And I verify the error message indicates "Forbidden" or "Unauthorized"

  @api @performance
  Scenario: Performance - Response Time Threshold Verification
    When I send a GET request to retrieve all bookings
    Then I verify the response time is less than 2000 milliseconds
