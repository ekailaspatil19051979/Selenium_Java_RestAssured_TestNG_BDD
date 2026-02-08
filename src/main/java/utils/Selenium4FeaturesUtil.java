package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v119.network.Network;
import org.openqa.selenium.devtools.v119.emulation.Emulation;
import org.openqa.selenium.devtools.v119.performance.Performance;
import org.openqa.selenium.devtools.v119.performance.model.Metric;

import java.util.List;
import java.util.Optional;

public class Selenium4FeaturesUtil {

    /**
     * Selenium 4: New Window / Tab Support
     */
    public static void openNewTab(WebDriver driver) {
        driver.switchTo().newWindow(WindowType.TAB);
    }

    public static void openNewWindow(WebDriver driver) {
        driver.switchTo().newWindow(WindowType.WINDOW);
    }

    /**
     * Selenium 4: Network Interception (CDP)
     */
    public static void enableNetworkInterception(WebDriver driver) {
        if (driver instanceof HasDevTools) {
            DevTools devTools = ((HasDevTools) driver).getDevTools();
            devTools.createSession();
            devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

            devTools.addListener(Network.requestWillBeSent(), request -> {
                LoggerUtil.info("Request URL: " + request.getRequest().getUrl());
                LoggerUtil.info("Request Method: " + request.getRequest().getMethod());
            });
        } else {
            LoggerUtil.error("Driver does not support DevTools");
        }
    }

    /**
     * Selenium 4: Geolocation Mocking (CDP)
     */
    public static void mockGeolocation(WebDriver driver, double latitude, double longitude, int accuracy) {
        if (driver instanceof HasDevTools) {
            DevTools devTools = ((HasDevTools) driver).getDevTools();
            devTools.createSession();
            devTools.send(Emulation.setGeolocationOverride(
                    Optional.of(latitude),
                    Optional.of(longitude),
                    Optional.of(accuracy)));
        } else {
            LoggerUtil.error("Driver does not support DevTools");
        }
    }

    /**
     * Selenium 4: Get Performance Metrics (CDP)
     */
    public static void logPerformanceMetrics(WebDriver driver) {
        if (driver instanceof HasDevTools) {
            DevTools devTools = ((HasDevTools) driver).getDevTools();
            devTools.createSession();
            devTools.send(Performance.enable(Optional.empty()));
            List<Metric> metrics = devTools.send(Performance.getMetrics());

            for (Metric metric : metrics) {
                LoggerUtil.info(metric.getName() + " : " + metric.getValue());
            }
        } else {
            LoggerUtil.error("Driver does not support DevTools");
        }
    }
}
