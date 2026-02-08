package stepdefinitions;

import base.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import utils.Selenium4FeaturesUtil;
import utils.LoggerUtil;

public class Selenium4StepDefs {

    private LoginPage loginPage = new LoginPage();

    @When("I login using relative locators")
    public void loginUsingRelativeLocators() {
        loginPage.loginWithRelativeLocators("admin", "password");
    }

    @When("I open a new tab and navigate to {string}")
    public void openNewTabAndNavigate(String url) {
        Selenium4FeaturesUtil.openNewTab(DriverFactory.getDriver());
        DriverFactory.getDriver().get(url);
        LoggerUtil.info("Opened new tab and navigated to " + url);
    }

    @Given("I enable network interception to monitor requests")
    public void enableNetworkInterception() {
        Selenium4FeaturesUtil.enableNetworkInterception(DriverFactory.getDriver());
    }

    @Then("I log the browser performance metrics")
    public void logPerformanceMetrics() {
        Selenium4FeaturesUtil.logPerformanceMetrics(DriverFactory.getDriver());
    }

    @Given("I mock my geolocation to latitude {double} and longitude {double}")
    public void mockLocation(double lat, double lon) {
        Selenium4FeaturesUtil.mockGeolocation(DriverFactory.getDriver(), lat, lon, 100);
        LoggerUtil.info("Geolocation mocked to: " + lat + ", " + lon);
    }
}
