package tests;

import io.restassured.RestAssured;
import models.Booking;
import models.BookingDates;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class BookingSchemaValidationTest {

    @Test
    public void validateBookingSchema() {

        BookingDates dates = new BookingDates();
        dates.setCheckin("2024-03-01");
        dates.setCheckout("2024-03-05");

        Booking booking = new Booking();
        booking.setFirstname("SchemaUser");
        booking.setLastname("Test");
        booking.setTotalprice(2000);
        booking.setDepositpaid(true);
        booking.setBookingdates(dates);

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        given()
            .header("Content-Type", "application/json")
            .body(booking)
        .when()
            .post("/booking")
        .then()
            .statusCode(200)
            .assertThat()
            .body(matchesJsonSchemaInClasspath("bookingSchema.json"));
    }
}
