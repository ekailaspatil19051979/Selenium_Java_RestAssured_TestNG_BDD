package stepdefinitions;

import base.APIBase;
import base.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.LoggerUtil;

public class Hooks {

    @Before
    public void setup(Scenario scenario) {
        LoggerUtil.info("Starting Scenario: " + scenario.getName());
        APIBase.setup();
        if (scenario.getSourceTagNames().contains("@ui")) {
            DriverFactory.initDriver();
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed() && scenario.getSourceTagNames().contains("@ui")) {
            byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Screenshot");
        }
        DriverFactory.quitDriver();
        LoggerUtil.info("Completed Scenario: " + scenario.getName());
    }
}
