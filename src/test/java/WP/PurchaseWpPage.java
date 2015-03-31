package WP;

import Shared.WaitPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * Created by kruzhitskaya on 31.03.15.
 */
public class PurchaseWpPage {
    public static void purchaseLoginUser(WebDriver driver) {

        String checkoutLabelXpath = ".//*[@id='cart-widget-links']/a";
        driver.findElement(By.xpath(checkoutLabelXpath)).click();//click Checkout label

        String purchaseButtonXpath = ".//*[@id='wpsc_shopping_cart_container']/form/div[2]/span/input[3]";
        WaitPage.waitElementClickable(purchaseButtonXpath, driver);
        driver.findElement(By.xpath(purchaseButtonXpath)).click();//click on Purchase button

        String confirmationPageXpath = ".//*[@id='post-6']";
        WaitPage.waitElementLocated(confirmationPageXpath, driver);
        Assert.assertTrue(driver.findElement(By.xpath(confirmationPageXpath)).isDisplayed());//check purchase confirmation

    }
}
