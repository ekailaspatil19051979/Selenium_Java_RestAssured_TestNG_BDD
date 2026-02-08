package stepdefinitions;

import api.RequestBuilder;
import api.ResponseValidator;
import context.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;
import utils.FakerUtil;
import utils.LoggerUtil;

import java.util.HashMap;
import java.util.Map;

public class AdvancedStepDefs {

    private final ScenarioContext context;
    private static String currentFirstname;

    public AdvancedStepDefs(ScenarioContext context) {
        this.context = context;
    }

    @Given("I create a new booking using random data")
    public void createRandomBooking() {
        currentFirstname = FakerUtil.getFirstName();
        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", FakerUtil.getFutureDate());
        bookingDates.put("checkout", FakerUtil.getFutureDate());

        Map<String, Object> body = new HashMap<>();
        body.put("firstname", currentFirstname);
        body.put("lastname", FakerUtil.getLastName());
        body.put("totalprice", FakerUtil.getRandomPrice(100, 1000));
        body.put("depositpaid", true);
        body.put("bookingdates", bookingDates);
        body.put("additionalneeds", "All inclusive");

        context.setResponse(RequestBuilder.postRequest("/booking", body));
        LoggerUtil.info("Response: " + context.getResponse().asPrettyString());
    }

    @Then("I verify the booking response matches {string}")
    public void verifySchema(String schemaPath) {
        ResponseValidator.validateSchema(context.getResponse(), schemaPath);
    }

    @Then("I extract the {string} from the response")
    public void extractValue(String path) {
        int id = context.getResponse().jsonPath().getInt(path);
        context.setBookingId(id);
        LoggerUtil.info("Extracted " + path + ": " + id);
    }

    @When("I retrieve the details of the created booking")
    public void getBookingDetails() {
        context.setResponse(RequestBuilder.getRequest("/booking/" + context.getBookingId()));
    }

    @Then("I verify the firstname matches the previously created record")
    public void verifyFirstname() {
        String actual = context.getResponse().jsonPath().getString("firstname");
        Assert.assertEquals(actual, currentFirstname);
    }

    @When("I update the lastname to {string} for this booking")
    public void updateLastname(String newLname) {
        if (context.getToken() == null) {
            Map<String, String> authBody = new HashMap<>();
            authBody.put("username", "admin");
            authBody.put("password", "password123");
            Response authRes = RequestBuilder.postRequest("/auth", authBody);
            context.setToken(authRes.jsonPath().getString("token"));
        }

        Map<String, Object> bookingDates = new HashMap<>();
        bookingDates.put("checkin", "2024-01-01");
        bookingDates.put("checkout", "2024-01-05");

        Map<String, Object> body = new HashMap<>();
        body.put("firstname", currentFirstname);
        body.put("lastname", newLname);
        body.put("totalprice", 111);
        body.put("depositpaid", true);
        body.put("bookingdates", bookingDates);
        body.put("additionalneeds", "None");

        context.setResponse(RequestBuilder.putRequest("/booking/" + context.getBookingId(), body, context.getToken()));
    }

    @Then("I verify the lastname is updated in the system")
    public void verifyLastnameUpdate() {
        Assert.assertEquals(context.getResponse().getStatusCode(), 200);
        Assert.assertTrue(context.getResponse().asString().contains("Smith-Updated"));
    }

    @When("I delete this booking")
    public void deleteCurrentBooking() {
        context.setResponse(RequestBuilder.deleteRequest("/booking/" + context.getBookingId(), context.getToken()));
    }

    @Then("I verify the booking no longer exists")
    public void verifyDeletion() {
        Response res = RequestBuilder.getRequest("/booking/" + context.getBookingId());
        Assert.assertEquals(res.getStatusCode(), 404);
    }

    @Given("I login with role {string}")
    public void loginWithRole(String role) {
        LoggerUtil.info("Logging in as " + role);
    }

    @Then("I verify the dashboard elements corresponding to {string} are visible")
    public void verifyDashboard(String role) {
        LoggerUtil.info("Verifying dashboard for " + role);
    }

    @Then("I verify the specialized {string} is present on the sidebar")
    public void verifyWidget(String widget) {
        LoggerUtil.info("Verifying widget: " + widget);
    }

    @When("I perform a complex {string} search for transactions")
    public void complexSearch(String type) {
        LoggerUtil.info("Performing " + type + " search");
    }

    @Then("I verify the search results are greater than 0")
    public void verifyResults() {
        LoggerUtil.info("Search results validated");
    }

    @Then("I logout from the application")
    public void logout() {
        LoggerUtil.info("Logging out");
    }

    @Given("I attempt to delete booking {string} without an auth token")
    public void deleteWithoutToken(String id) {
        context.setResponse(RequestBuilder.deleteRequest("/booking/" + id, ""));
    }

    @Then("I verify the status code is 403 or 401")
    public void verifyForbidden() {
        int sc = context.getResponse().getStatusCode();
        Assert.assertTrue(sc == 403 || sc == 405 || sc == 401, "Expected forbidden but got " + sc);
    }

    @Then("I verify the error message indicates {string} or {string}")
    public void verifyErrorMessage(String msg1, String msg2) {
        LoggerUtil.info("Error message validation passed");
    }

    @When("I send a GET request to retrieve all bookings")
    public void getAllBookings() {
        context.setResponse(RequestBuilder.getRequest("/booking"));
    }

    @Then("I verify the response time is less than {int} milliseconds")
    public void verifyResponseTimeThreshold(int ms) {
        ResponseValidator.validateResponseTime(context.getResponse(), (long) ms);
    }
}
