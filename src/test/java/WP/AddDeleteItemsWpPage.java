package WP;

import Shared.WaitPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * Created by kruzhitskaya on 31.03.15.
 */
public class AddDeleteItemsWpPage {

    public static void addItem(WebDriver driver) {

        String addToCartButtonXpath = ".//*[@id='product_10_submit_button']";
        driver.findElement(By.xpath(addToCartButtonXpath)).click();//add item to the cart

        String cartItemsAmountXpath = ".//*[@id='sliding_cart']/div/table/tfoot/tr[1]/td[1]";
        WaitPage.waitElementLocated(cartItemsAmountXpath, driver);
        Assert.assertTrue(driver.findElement(By.xpath(cartItemsAmountXpath)).isDisplayed());// check cart items >0

    }

    public static void deleteItem(WebDriver driver) {

        String clearCartXpath = ".//*[@id='cart-widget-links']/form/a";
        driver.findElement(By.xpath(clearCartXpath)).click();

        String emptyCartXpath = ".//*[@id='sliding_cart']/p";
        WaitPage.waitElementLocated(emptyCartXpath, driver);
        Assert.assertEquals("Your shopping cart is empty\n" +
                "Visit the shop", driver.findElement(By.xpath(emptyCartXpath)).getText());// check cart items = 0
    }

    public static void clickLogo(WebDriver driver) {

        String logoXpath = ".//*[@id='masthead']/div/div/h1/a";
        driver.findElement(By.xpath(logoXpath)).click();//click on logo

        String addToCartButtonXpath = ".//*[@id='product_10_submit_button']";
        WaitPage.waitElementLocated(addToCartButtonXpath, driver);
    }
}
