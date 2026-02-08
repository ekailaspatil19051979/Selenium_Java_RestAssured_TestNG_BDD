package api;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import utils.LoggerUtil;

public class CustomLogFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
            FilterContext ctx) {

        LoggerUtil.info("--- REQUEST DETAILS ---");
        LoggerUtil.info("Method: " + requestSpec.getMethod());
        LoggerUtil.info("URI: " + requestSpec.getURI());
        if (requestSpec.getBody() != null) {
            LoggerUtil.info("Body: " + requestSpec.getBody().toString());
        }

        Response response = ctx.next(requestSpec, responseSpec);

        LoggerUtil.info("--- RESPONSE DETAILS ---");
        LoggerUtil.info("Status: " + response.getStatusCode());
        LoggerUtil.info("Response Body: " + response.asPrettyString());

        return response;
    }
}
