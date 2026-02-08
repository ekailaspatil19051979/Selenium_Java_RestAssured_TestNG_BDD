package api;

import io.restassured.response.Response;
import org.testng.Assert;
import utils.LoggerUtil;

public class ResponseValidator {

    public static void validateStatusCode(Response response, int expectedStatusCode) {
        LoggerUtil.info("Validating Status Code: " + expectedStatusCode);
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
    }

    public static void validateResponseTime(Response response, long expectedTime) {
        LoggerUtil.info("Validating Response Time < " + expectedTime);
        Assert.assertTrue(response.getTime() < expectedTime, "Response time exceeded");
    }

    public static String getValue(Response response, String jsonPath) {
        return response.jsonPath().getString(jsonPath);
    }
}
