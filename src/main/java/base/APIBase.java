package base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import config.ConfigReader;

public class APIBase {
    protected static RequestSpecification requestSpec;

    public static void setup() {
        RestAssured.baseURI = ConfigReader.getBaseUrl();
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getBaseUrl())
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
    }
}
