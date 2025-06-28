package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DriverManager {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            String browser = ConfigReader.get("browser");

            switch (browser.toLowerCase()) {
                case "firefox":
                    driver = new FirefoxDriver();
                    break;

                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--disable-gpu");

                    // Unique user-data-dir to avoid session conflict in CI
                    try {
                        Path tempProfile = Files.createTempDirectory("chrome-profile");
                        chromeOptions.addArguments("--user-data-dir=" + tempProfile.toAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    driver = new ChromeDriver(chromeOptions);
                    break;

                case "edge":
                default:
                    driver = new EdgeDriver();
                    break;
            }

            // Avoid maximizing in headless (especially in CI)
            if (!browser.equalsIgnoreCase("chrome") || System.getenv("CI") == null) {
                driver.manage().window().maximize();
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
