package base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import config.ConfigReader;
import utils.LoggerUtil;

public class BaseTest {

    @BeforeSuite
    public void globalSetup() {
        LoggerUtil.info("Starting Test Execution Suite");
        APIBase.setup();
    }

    @BeforeMethod
    public void setup() {
        DriverFactory.initDriver();
        LoggerUtil.info("Browser initialized: " + ConfigReader.getBrowser());
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
        LoggerUtil.info("Browser closed");
    }
}
