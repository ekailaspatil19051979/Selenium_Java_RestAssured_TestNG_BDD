package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Booking;
import models.BookingDates;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ExcelReader;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BookingExcelDrivenTest {

    @Test
    public void excelDrivenBookingTest() {

        String filePath = "src/test/resources/testdata.xlsx";

        List<Map<String, String>> excelData =
                ExcelReader.getExcelData(filePath, "Sheet1");

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        for (Map<String, String> row : excelData) {

            String fname = row.get("FirstName");
            String lname = row.get("LastName");
            int price = Integer.parseInt(row.get("Price"));

            BookingDates dates = new BookingDates();
            dates.setCheckin("2024-05-01");
            dates.setCheckout("2024-05-10");

            Booking booking = new Booking();
            booking.setFirstname(fname);
            booking.setLastname(lname);
            booking.setTotalprice(price);
            booking.setDepositpaid(true);
            booking.setBookingdates(dates);

            Response response =
            given()
                .header("Content-Type", "application/json")
                .body(booking)
            .when()
                .post("/booking")
            .then()
                .statusCode(200)
                .extract()
                .response();

            Booking res = response.as(Booking.class);

            Assert.assertEquals(res.getFirstname(), fname);
        }
    }
}
