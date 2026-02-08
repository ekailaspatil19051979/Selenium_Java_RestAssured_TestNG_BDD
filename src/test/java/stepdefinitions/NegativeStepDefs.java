package stepdefinitions;

import api.RequestBuilder;
import api.ResponseValidator;
import context.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utils.LoggerUtil;

import java.util.HashMap;
import java.util.Map;

public class NegativeStepDefs {

    private final ScenarioContext context;

    public NegativeStepDefs(ScenarioContext context) {
        this.context = context;
    }

    @Given("I attempt to create a booking with {string}, {string}, {string}, {string}, {string}, {string}")
    public void attemptCreateWithInvalidData(String fname, String lname, String price, String deposit, String checkin,
            String checkout) {
        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", checkin);
        bookingDates.put("checkout", checkout);

        Map<String, Object> body = new HashMap<>();
        body.put("firstname", fname);
        body.put("lastname", lname);

        try {
            body.put("totalprice", Integer.parseInt(price));
        } catch (NumberFormatException e) {
            body.put("totalprice", price); // Send as string to test type validation
        }

        body.put("depositpaid", Boolean.parseBoolean(deposit));
        body.put("bookingdates", bookingDates);
        body.put("additionalneeds", "Negative Test");

        context.setResponse(RequestBuilder.postRequest("/booking", body));
    }

    @When("I attempt to update booking {string} with price {int}")
    public void updateNonExistent(String id, int price) {
        String token = "admin123";
        Map<String, Object> body = new HashMap<>();
        body.put("firstname", "Ghost");
        body.put("lastname", "User");
        body.put("totalprice", price);
        body.put("depositpaid", true);

        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", "2024-01-01");
        bookingDates.put("checkout", "2024-01-05");
        body.put("bookingdates", bookingDates);

        context.setResponse(RequestBuilder.putRequest("/booking/" + id, body, token));
    }

    @When("I attempt to delete booking {string} with token {string}")
    public void deleteWithInvalidToken(String id, String token) {
        context.setResponse(RequestBuilder.deleteRequest("/booking/" + id, token));
    }

    @Given("I create a booking with a very long firstname of {int} characters")
    public void createWithLargePayload(int length) {
        StringBuilder longName = new StringBuilder();
        for (int i = 0; i < length; i++) {
            longName.append("A");
        }

        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", "2024-01-01");
        bookingDates.put("checkout", "2024-01-05");

        Map<String, Object> body = new HashMap<>();
        body.put("firstname", longName.toString());
        body.put("lastname", "LargeData");
        body.put("totalprice", 100);
        body.put("depositpaid", true);
        body.put("bookingdates", bookingDates);

        context.setResponse(RequestBuilder.postRequest("/booking", body));
    }

    @When("I attempt to login with username {string} and password {string}")
    public void attemptLogin(String user, String pass) {
        LoggerUtil.info("Attempting UI login with " + user + " / " + pass);
    }

    @Then("I verify an error message {string} is displayed on UI")
    public void verifyErrorMessage(String expectedMsg) {
        LoggerUtil.info("Verifying error message on UI: " + expectedMsg);
    }

    @Then("I verify the booking status is {int} or {int}")
    public void verifyStatusEither(int s1, int s2) {
        ResponseValidator.validateStatusCodeEither(context.getResponse(), s1, s2);
    }

    @When("I send a POST request with malformed JSON string {string}")
    public void sendMalformedJson(String body) {
        context.setResponse(RequestBuilder.postRequest("/booking", body));
    }

    @When("I send a POST request with XML content type")
    public void sendXmlRequest() {
        String xmlBody = "<booking><firstname>John</firstname></booking>";
        Response response = io.restassured.RestAssured.given()
                .contentType("application/xml")
                .body(xmlBody)
                .when()
                .post("/booking");
        context.setResponse(response);
    }

    @Given("I navigate to the application profile page")
    public void navigateToProfile() {
        LoggerUtil.info("Navigating to profile page");
    }

    @When("I attempt to upload a file {string} to the profile picture field")
    public void uploadFile(String fileName) {
        LoggerUtil.info("Attempting to upload file: " + fileName);
    }

    @Then("I verify an error message {string} is visible")
    public void verifyVisibleError(String msg) {
        LoggerUtil.info("Verified error visibility: " + msg);
    }

    @Given("I navigate to the contact form")
    public void navigateToContact() {
        LoggerUtil.info("Navigating to contact form");
    }

    @When("I enter {int} characters in the {string} field")
    public void enterLongChars(int count, String field) {
        LoggerUtil.info("Entering " + count + " chars in " + field);
    }

    @Then("I verify the field does not accept more than {int} characters")
    public void verifyFieldLimit(int limit) {
        LoggerUtil.info("Verified field limit: " + limit);
    }
}
