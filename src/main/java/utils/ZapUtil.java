package utils;

import io.restassured.RestAssured;
import io.restassured.specification.ProxySpecification;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeOptions;

public class ZapUtil {

    private static final String ZAP_PROXY_HOST = "localhost";
    private static final int ZAP_PROXY_PORT = 8080;

    public static void setRestAssuredProxy() {
        LoggerUtil.info("Configuring Rest Assured to use ZAP Proxy at " + ZAP_PROXY_HOST + ":" + ZAP_PROXY_PORT);
        RestAssured.proxy = ProxySpecification.host(ZAP_PROXY_HOST).withPort(ZAP_PROXY_PORT);
    }

    public static ChromeOptions getZapChromeOptions() {
        LoggerUtil.info("Configuring Chrome to use ZAP Proxy");
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(ZAP_PROXY_HOST + ":" + ZAP_PROXY_PORT);
        proxy.setSslProxy(ZAP_PROXY_HOST + ":" + ZAP_PROXY_PORT);

        ChromeOptions options = new ChromeOptions();
        options.setProxy(proxy);
        options.setAcceptInsecureCerts(true);
        return options;
    }
}
