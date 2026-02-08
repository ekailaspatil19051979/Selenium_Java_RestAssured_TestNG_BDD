package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitFactory {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);
    private static final Duration POLLING_INTERVAL = Duration.ofMillis(500);

    public static WebElement waitForElementToBeVisible(WebDriver driver, By locator) {
        return new WebDriverWait(driver, DEFAULT_TIMEOUT)
                .pollingEvery(POLLING_INTERVAL)
                .ignoring(org.openqa.selenium.NoSuchElementException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForElementToBeClickable(WebDriver driver, By locator) {
        return new WebDriverWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void waitForPageLoad(WebDriver driver) {
        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500))
                .until(d -> ((org.openqa.selenium.JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));
    }
}
