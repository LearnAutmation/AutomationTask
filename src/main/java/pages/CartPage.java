package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;

public class CartPage {
    WebDriver driver;
    String firstName = ConfigReader.get("aut.firstName");
    String lastName = ConfigReader.get("aut.lastName");
    String zip = ConfigReader.get("aut.zip");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickCheckout() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.id("checkout")))
                .click();
    }

    public void fillCheckoutInfo() {
        driver.findElement(By.id("first-name")).sendKeys(firstName);
        driver.findElement(By.id("last-name")).sendKeys(lastName);
        driver.findElement(By.id("postal-code")).sendKeys(zip);
        driver.findElement(By.id("continue")).click();
    }

    public void clickFinish() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.id("finish")))
                .click();
    }

    public String getFullThankYouMessage() {
        String title = driver.findElement(By.className("complete-header")).getText();
        String subtitle = driver.findElement(By.className("complete-text")).getText();
        return title + " " + subtitle;
    }

    public void clickBackHome() {
        driver.findElement(By.id("back-to-products")).click();
    }

}
