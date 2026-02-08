package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.LoggerUtil;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class RequestBuilder {

    static {
        RestAssured.filters(new CustomLogFilter());
    }

    public static Response getRequest(String endpoint) {
        LoggerUtil.info("Sending GET request to: " + endpoint);
        return given().when().get(endpoint);
    }

    public static Response postRequest(String endpoint, Object body) {
        LoggerUtil.info("Sending POST request to: " + endpoint);
        return given().contentType("application/json").body(body).when().post(endpoint);
    }

    public static Response postRequest(String endpoint, Object body, Map<String, String> headers) {
        LoggerUtil.info("Sending POST request to: " + endpoint);
        return given().headers(headers).contentType("application/json").body(body).when().post(endpoint);
    }

    public static Response putRequest(String endpoint, Object body, String token) {
        LoggerUtil.info("Sending PUT request to: " + endpoint);
        return given().cookie("token", token).contentType("application/json").body(body).when().put(endpoint);
    }

    public static Response deleteRequest(String endpoint, String token) {
        LoggerUtil.info("Sending DELETE request to: " + endpoint);
        return given().cookie("token", token).when().delete(endpoint);
    }

    public static Response patchRequest(String endpoint, Object body, String token) {
        LoggerUtil.info("Sending PATCH request to: " + endpoint);
        return given().cookie("token", token).contentType("application/json").body(body).when().patch(endpoint);
    }
}
