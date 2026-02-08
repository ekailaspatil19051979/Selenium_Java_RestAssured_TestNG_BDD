package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int maxRetryCount = 1; // You can move this to config.properties

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            LoggerUtil.info("Retrying test: " + result.getName() + " for the " + retryCount + " time.");
            return true;
        }
        return false;
    }
}
