Feature: Dynamic Data Testing

  @api @dynamic
  Scenario: Create booking with dynamic data
    Given I create a booking with "dynamic", "dynamic", "dynamic", "true", "dynamic", "dynamic"
    When I verify the booking status is 200
    And I have a valid auth token
    And I update the booking with "John", "Doe", 200
    Then I verify the booking price is updated to 200

  @api @dynamic @negative
  Scenario: Create booking with invalid dynamic data (Negative Test)
    # Testing with a very low price to see if system handles it, or just generating random data
    Given I create a booking with "dynamic", "dynamic", "1", "false", "2024-01-01", "2024-01-02"
    When I verify the booking status is 200
