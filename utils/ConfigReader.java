package utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties;

    static {
        try {
            FileInputStream fis = new FileInputStream("./resources/config.properties");
            properties = new Properties();
            properties.load(fis);
        } catch (Exception e) {
            throw new RuntimeException("Config file not found!");
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
