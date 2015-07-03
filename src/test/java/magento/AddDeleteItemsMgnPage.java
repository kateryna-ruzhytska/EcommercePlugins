package magento;

import shared.WaitPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * Created by kruzhitskaya on 30.03.15.
 */
public class AddDeleteItemsMgnPage {

    public static void addItem(WebDriver driver) {

        String addItemButtonXpath = ".//*[@id='top']/body/div[1]/div/div[2]/div/div[2]/div[2]/div[2]/ul/li/div/div[2]/button";
        WaitPage.waitElementClickable(addItemButtonXpath, driver);
        driver.findElement(By.xpath(addItemButtonXpath)).click();//add item to the cart

        String cartItemsAmountXpath = ".//*[@id='header']/div/div[2]/div/div/a/span[3]";
        WaitPage.waitElementLocated(cartItemsAmountXpath, driver);
        Assert.assertTrue(driver.findElement(By.xpath(cartItemsAmountXpath)).isDisplayed());//check cart items >0;

    }

    public static void deleteItem(WebDriver driver) {

        By cartLabelXpath = By.xpath(".//*[@id='header']/div/div[2]/div/div/a/span[2]");
        driver.findElement(cartLabelXpath).click();; //view cart options

        By viewShoppingCartXpath = By.xpath(".//*[@id='header-cart']/div[3]/div[4]/a");
        driver.findElement(viewShoppingCartXpath).click();//view shopping cart

        String emptyCartButtonXpath= ".//*[@id='empty_cart_button']";
        WaitPage.waitElementLocated(emptyCartButtonXpath, driver);
        driver.findElement(By.xpath(emptyCartButtonXpath)).click();// click Empty cart

        String cartItemsAmountXpath = ".//*[@id='header']/div/div[2]/div/div/a/span[3]";
        WaitPage.waitElementLocated(cartItemsAmountXpath, driver);
        Assert.assertFalse(driver.findElement(By.xpath(cartItemsAmountXpath)).isDisplayed());//check cart is empty

    }
}
