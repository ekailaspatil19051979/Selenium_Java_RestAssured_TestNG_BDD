package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/test/resources/config.properties";

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE_PATH);
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load configuration file.");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getBaseUrl() {
        return getProperty("baseurl");
    }

    public static String getBrowser() {
        return getProperty("browser");
    }
    
    public static int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait"));
    }
    
    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless"));
    }
}
