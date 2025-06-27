package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.ConfigReader;
import utils.DriverManager;
import java.util.logging.Logger;


public class UITest {
    private static final Logger logger = Logger.getLogger(UITest.class.getName());


    @Test
    public void completeFlowTest() {
        String baseUrl = ConfigReader.get("aut.baseUrl");
        String username = ConfigReader.get("aut.username");
        String password = ConfigReader.get("aut.password");

        WebDriver driver = DriverManager.getDriver();
        logger.info("Navigating to the URl :"+baseUrl);
        driver.get(baseUrl);

        logger.info("Entering login credentials");
        LoginPage login = new LoginPage(driver);
        login.login(username, password);

        /*
        Test Case 1: Assert that user landing on inventory page
         */
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));

        logger.info("Adding item to the cart");
        InventoryPage inventory = new InventoryPage(driver);
        inventory.addToCartFirstItem();
        /*
         Test Case 2: Assert that cart badge shows 1
         */
        WebElement badge = driver.findElement(By.className("shopping_cart_badge"));
        Assert.assertEquals(badge.getText(), "1");
        inventory.goToCart();

        logger.info("Completing the checkout process");
        CartPage cart = new CartPage(driver);
        cart.clickCheckout();
        cart.fillCheckoutInfo();

        /*
        Test Case 3: Assert that checkout overview page and products
         */
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two"));
        Assert.assertFalse(driver.findElements(By.className("inventory_item_name")).isEmpty());

        cart.clickFinish();

        /*
        Test Case 4: Final thank-you message
         */

        logger.info("Validating the placed order with message");
        String message = cart.getFullThankYouMessage();
        Assert.assertEquals(message, ConfigReader.get("ui.checkout.fullMessage"));

        /*
        Test Case5 : optional: verify back to inventory page
         */
        cart.clickBackHome();
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
