package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Booking;
import models.BookingDates;

import static io.restassured.RestAssured.given;

public class BookingSteps {

    Booking booking;
    Response response;

    @Given("booking details are prepared")
    public void prepareBooking() {

        BookingDates dates = new BookingDates();
        dates.setCheckin("2024-06-01");
        dates.setCheckout("2024-06-05");

        booking = new Booking();
        booking.setFirstname("BDD");
        booking.setLastname("User");
        booking.setTotalprice(1300);
        booking.setDepositpaid(true);
        booking.setBookingdates(dates);
    }

    @When("user calls create booking API")
    public void callAPI() {

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        response =
        given()
            .header("Content-Type", "application/json")
            .body(booking)
        .when()
            .post("/booking");
    }

    @Then("booking should be created successfully")
    public void validate() {
        response.then().statusCode(200);
    }
}
