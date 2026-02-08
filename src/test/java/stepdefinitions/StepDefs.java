package stepdefinitions;

import api.RequestBuilder;
import api.ResponseValidator;
import base.DriverFactory;
import config.ConfigReader;
import context.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;
import utils.LoggerUtil;

import java.util.HashMap;
import java.util.Map;

public class StepDefs {

    private final ScenarioContext context;

    public StepDefs(ScenarioContext context) {
        this.context = context;
    }

    @Given("I create a booking with {string}, {string}, {string}, {string}, {string}, {string}")
    public void createBooking(String fname, String lname, String price, String deposit, String checkin,
            String checkout) {

        if (fname.equalsIgnoreCase("dynamic"))
            fname = utils.FakerUtil.getFirstName();
        if (lname.equalsIgnoreCase("dynamic"))
            lname = utils.FakerUtil.getLastName();
        if (price.equalsIgnoreCase("dynamic"))
            price = String.valueOf(utils.FakerUtil.getRandomPrice(50, 500));
        if (checkin.equalsIgnoreCase("dynamic"))
            checkin = utils.FakerUtil.getFutureDate();
        if (checkout.equalsIgnoreCase("dynamic"))
            checkout = utils.FakerUtil.getFutureDate();

        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", checkin);
        bookingDates.put("checkout", checkout);

        Map<String, Object> body = new HashMap<>();
        body.put("firstname", fname);
        body.put("lastname", lname);
        body.put("totalprice", Integer.parseInt(price));
        body.put("depositpaid", Boolean.parseBoolean(deposit));
        body.put("bookingdates", bookingDates);
        body.put("additionalneeds", "Breakfast");

        context.setResponse(RequestBuilder.postRequest("/booking", body));
        context.setBookingId(context.getResponse().jsonPath().getInt("bookingid"));
        LoggerUtil.info("Booking created with ID: " + context.getBookingId());
        LoggerUtil.info("Booking Details: " + fname + " " + lname + " Price: " + price);
    }

    @When("I verify the booking status is {int}")
    public void verifyStatus(int status) {
        ResponseValidator.validateStatusCode(context.getResponse(), status);
    }

    @Then("I navigate to the application URL")
    public void navigateToApp() {
        DriverFactory.getDriver().get(ConfigReader.getBaseUrl());
    }

    @Given("I navigate to {string}")
    public void navigateToUrl(String url) {
        DriverFactory.getDriver().get(url);
    }

    @Then("I verify the booking is displayed on the UI")
    public void verifyOnUI() {
        LoggerUtil.info("Verifying booking ID " + context.getBookingId() + " on UI");
    }

    @Given("I have a valid auth token")
    public void getAuthToken() {
        Map<String, String> body = new HashMap<>();
        body.put("username", ConfigReader.getProperty("username"));
        body.put("password", ConfigReader.getProperty("password"));

        Response authResponse = RequestBuilder.postRequest("/auth", body);
        context.setToken(authResponse.jsonPath().getString("token"));
        LoggerUtil.info("Auth Token generated: " + context.getToken());
    }

    @When("I update the booking with {string}, {string}, {int}")
    public void updateBooking(String fname, String lname, int price) {
        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", "2024-01-01");
        bookingDates.put("checkout", "2024-01-05");

        Map<String, Object> body = new HashMap<>();
        body.put("firstname", fname);
        body.put("lastname", lname);
        body.put("totalprice", price);
        body.put("depositpaid", true);
        body.put("bookingdates", bookingDates);

        context.setResponse(RequestBuilder.putRequest("/booking/" + context.getBookingId(), body, context.getToken()));
    }

    @Then("I verify the booking price is updated to {int}")
    public void verifyPrice(int expectedPrice) {
        // Handle both create response (wrapped in booking object) and update response
        // (direct)
        Integer actualPrice;
        if (context.getResponse().jsonPath().get("booking.totalprice") != null) {
            actualPrice = context.getResponse().jsonPath().getInt("booking.totalprice");
        } else {
            actualPrice = context.getResponse().jsonPath().getInt("totalprice");
        }
        Assert.assertEquals(actualPrice.intValue(), expectedPrice, "Price mismatch!");
        LoggerUtil.info("Actual Price: " + actualPrice);
    }

    @Then("I delete the booking")
    public void deleteBooking() {
        context.setResponse(RequestBuilder.deleteRequest("/booking/" + context.getBookingId(), context.getToken()));
    }
}
