Feature: Booking API

  Scenario: Create Booking using BDD
    Given booking details are prepared
    When user calls create booking API
    Then booking should be created successfully
