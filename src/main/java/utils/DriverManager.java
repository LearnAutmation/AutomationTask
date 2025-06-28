package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class DriverManager {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            try {
                String browser = ConfigReader.get("browser").toLowerCase();
                boolean useGrid = Boolean.parseBoolean(ConfigReader.get("use.grid"));

                if (useGrid) {
                    String gridUrl = ConfigReader.get("grid.url");
                    DesiredCapabilities capabilities = new DesiredCapabilities();
                    capabilities.setBrowserName(browser);

                    driver = new RemoteWebDriver(new URL(gridUrl), capabilities);
                } else {
                    switch (browser) {
                        case "chrome":
                            driver = new ChromeDriver();
                            break;
                        case "firefox":
                            driver = new FirefoxDriver();
                            break;
                        case "edge":
                            driver = new EdgeDriver();
                            break;
                        default:
                            throw new RuntimeException("Unsupported browser: " + browser);
                    }
                }

                driver.manage().window().maximize();
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize WebDriver", e);
            }
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
