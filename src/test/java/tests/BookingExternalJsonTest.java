
package tests;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class BookingExternalJsonTest {

    @Test
    public void externalJsonDrivenTest() throws Exception {

        String jsonBody = new String(
            Files.readAllBytes(Paths.get("src/test/resources/bookingPayload.json"))
        );

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        given()
            .header("Content-Type", "application/json")
            .body(jsonBody)
        .when()
            .post("/booking")
        .then()
            .statusCode(200);
    }
}
