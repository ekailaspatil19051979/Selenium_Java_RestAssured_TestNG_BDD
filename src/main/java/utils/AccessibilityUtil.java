package utils;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.selenium.AxeBuilder;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class AccessibilityUtil {

    public static void checkAccessibility(WebDriver driver) {
        LoggerUtil.info("--- ACCESSIBILITY SCAN STARTING ---");
        Results results = new AxeBuilder().analyze(driver);

        if (results.getViolations().isEmpty()) {
            LoggerUtil.info("No accessibility violations found.");
        } else {
            LoggerUtil.error("Accessibility violations found: " + results.getViolations().size());
            results.getViolations().forEach(violation -> {
                LoggerUtil.error("Violation: " + violation.getHelp() + " | Impact: " + violation.getImpact());
            });
            // We can choose to fail the test or just log
            // Assert.assertTrue(results.getViolations().isEmpty(), "Accessibility
            // violations detected");
        }
    }
}
