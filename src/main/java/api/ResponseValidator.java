package api;

import io.restassured.response.Response;
import org.testng.Assert;
import utils.LoggerUtil;

public class ResponseValidator {

    public static void validateStatusCode(Response response, int expectedStatusCode) {
        Assert.assertNotNull(response, "Response is NULL! Request might have failed.");
        LoggerUtil.info("Validating Status Code: " + expectedStatusCode);
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
    }

    public static void validateStatusCodeEither(Response response, int s1, int s2) {
        Assert.assertNotNull(response, "Response is NULL! Request might have failed.");
        int actual = response.getStatusCode();
        LoggerUtil.info("Validating Status Code: " + s1 + " or " + s2 + " (Actual: " + actual + ")");
        Assert.assertTrue(actual == s1 || actual == s2, "Expected " + s1 + " or " + s2 + " but found " + actual);
    }

    public static void validateResponseTime(Response response, long expectedTime) {
        LoggerUtil.info("Validating Response Time < " + expectedTime);
        Assert.assertTrue(response.getTime() < expectedTime, "Response time exceeded");
    }

    public static void validateSchema(Response response, String schemaPath) {
        LoggerUtil.info("Validating JSON Schema: " + schemaPath);
        response.then().assertThat()
                .body(io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
    }

    public static void validateFieldExists(Response response, String jsonPath) {
        LoggerUtil.info("Validating field exists: " + jsonPath);
        Assert.assertNotNull(response.jsonPath().get(jsonPath), "Field " + jsonPath + " does not exist");
    }

    public static String getValue(Response response, String jsonPath) {
        return response.jsonPath().getString(jsonPath);
    }
}
