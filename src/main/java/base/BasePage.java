package base;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import config.ConfigReader;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        // Driver might not be initialized at object creation time
        this.driver = DriverFactory.getDriver();

        if (this.driver != null) {
            this.wait = new WebDriverWait(driver,
                    Duration.ofSeconds(ConfigReader.getExplicitWait()));

            PageFactory.initElements(driver, this);
        }
    }

    private WebDriverWait getWait() {
        if (wait == null) {
            this.driver = DriverFactory.getDriver();

            if (driver != null) {
                wait = new WebDriverWait(driver,
                        Duration.ofSeconds(ConfigReader.getExplicitWait()));
            }
        }
        return wait;
    }

    public void waitForVisibility(WebElement element) {
        getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public void click(WebElement element) {
        waitForVisibility(element);
        element.click();
    }

    public void sendKeys(WebElement element, String text) {
        waitForVisibility(element);
        element.clear();
        element.sendKeys(text);
    }

    public String getText(WebElement element) {
        waitForVisibility(element);
        return element.getText();
    }

    /**
     * Selenium 4 Feature: Access Shadow DOM
     */
    public SearchContext getShadowRoot(WebElement shadowHost) {
        return shadowHost.getShadowRoot();
    }
}
