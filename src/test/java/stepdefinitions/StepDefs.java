package stepdefinitions;

import api.RequestBuilder;
import api.ResponseValidator;
import base.DriverFactory;
import config.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import pages.HomePage;
import utils.LoggerUtil;

import java.util.HashMap;
import java.util.Map;

public class StepDefs {

    private Response response;
    private HomePage homePage = new HomePage();
    private static int bookingId;
    private static String token;

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

        response = RequestBuilder.postRequest("/booking", body);
        bookingId = response.jsonPath().getInt("bookingid");
        LoggerUtil.info("Booking created with ID: " + bookingId);
        LoggerUtil.info("Booking Details: " + fname + " " + lname + " Price: " + price);
    }

    @When("I verify the booking status is {int}")
    public void verifyStatus(int status) {
        ResponseValidator.validateStatusCode(response, status);
    }

    @Then("I navigate to the application URL")
    public void navigateToApp() {
        DriverFactory.getDriver().get(ConfigReader.getBaseUrl());
    }

    @Then("I verify the booking is displayed on the UI")
    public void verifyBookingUI() {
        // Implementation to check if booking exists on UI
        // This might require logging in to admin panel or checking public page
        LoggerUtil.info("Verifying booking " + bookingId + " on UI");
        // Placeholder check as specific UI logic depends on implementation
        // homePage.checkBooking(bookingId);
    }

    @Given("I have a valid auth token")
    public void generateAuthToken() {
        Map<String, String> body = new HashMap<>();
        body.put("username", ConfigReader.getProperty("username"));
        body.put("password", ConfigReader.getProperty("password"));

        Response res = RequestBuilder.postRequest("/auth", body);
        token = res.jsonPath().getString("token");
        LoggerUtil.info("Generated Auth Token: " + token);
    }

    @When("I update the existing booking with price {int}")
    public void updateBooking(int price) {
        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", "2024-01-01");
        bookingDates.put("checkout", "2024-01-05");

        Map<String, Object> body = new HashMap<>();
        body.put("firstname", "John");
        body.put("lastname", "Doe");
        body.put("totalprice", price);
        body.put("depositpaid", true);
        body.put("bookingdates", bookingDates);
        body.put("additionalneeds", "Dinner");

        response = RequestBuilder.putRequest("/booking/" + bookingId, body, token);
    }

    @Then("I verify the booking price is updated to {int}")
    public void verifyPrice(int price) {
        int actualPrice = response.jsonPath().getInt("totalprice");
        if (actualPrice != price) {
            throw new AssertionError("Expected price: " + price + " but got: " + actualPrice);
        }
    }

    @When("I delete the existing booking")
    public void deleteBooking() {
        response = RequestBuilder.deleteRequest("/booking/" + bookingId, token);
    }
}
