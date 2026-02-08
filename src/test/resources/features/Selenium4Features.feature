@Selenium4
Feature: Selenium 4 Features Showcase
  As a tester
  I want to use Selenium 4 features
  So that I can write more efficient and powerful tests

  Scenario: Using Relative Locators for Login
    Given I navigate to "https://automationexercise.com/login"
    When I login using relative locators
    Then I verify the dashboard elements corresponding to "admin" are visible

  Scenario: Managing Multiple Windows and Tabs
    Given I navigate to "https://www.google.com"
    When I open a new tab and navigate to "https://www.bing.com"
    Then I log the browser performance metrics

  Scenario: Using CDP for Network Interception and Geolocation
    Given I enable network interception to monitor requests
    And I mock my geolocation to latitude 40.7128 and longitude -74.0060
    When I navigate to "https://www.google.com/maps"
    Then I log the browser performance metrics
