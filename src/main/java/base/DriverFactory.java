package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import config.ConfigReader;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    public static void initDriver() {

        // If driver already exists for this thread, do not re-create
        if (threadLocalDriver.get() != null) {
            return;
        }

        String browser = ConfigReader.getBrowser();
        String gridUrl = System.getProperty("grid.url");

        try {
            if (gridUrl != null && !gridUrl.isEmpty()) {

                if (browser.equalsIgnoreCase("chrome")) {

                    ChromeOptions options = new ChromeOptions();

                    if (ConfigReader.isHeadless()) {
                        options.addArguments("--headless");
                    }

                    threadLocalDriver.set(
                            new RemoteWebDriver(new URL(gridUrl), options));

                } else if (browser.equalsIgnoreCase("firefox")) {

                    FirefoxOptions options = new FirefoxOptions();

                    if (ConfigReader.isHeadless()) {
                        options.addArguments("--headless");
                    }

                    threadLocalDriver.set(
                            new RemoteWebDriver(new URL(gridUrl), options));
                }

            } else {

                // Local execution
                if (browser.equalsIgnoreCase("chrome")) {

                    ChromeOptions options = new ChromeOptions();

                    if (ConfigReader.isHeadless()) {
                        options.addArguments("--headless");
                    }

                    threadLocalDriver.set(new ChromeDriver(options));

                } else if (browser.equalsIgnoreCase("firefox")) {

                    threadLocalDriver.set(new FirefoxDriver());

                } else {
                    throw new RuntimeException("Unsupported browser: " + browser);
                }
            }

            // Safety check before maximize
            if (getDriver() != null) {
                getDriver().manage().window().maximize();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid Grid URL: " + gridUrl);
        }
    }

    public static WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    public static void quitDriver() {

        WebDriver driver = getDriver();

        if (driver != null) {
            driver.quit();
            threadLocalDriver.remove();
        }
    }
}
