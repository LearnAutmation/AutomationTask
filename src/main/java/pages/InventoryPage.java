package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage {
    WebDriver driver;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public void addToCartFirstItem() {
        driver.findElement(By.cssSelector(".inventory_item button")).click();
    }

    public void goToCart() {
        driver.findElement(By.className("shopping_cart_link")).click();
    }
}
