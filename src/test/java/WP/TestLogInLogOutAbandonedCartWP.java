package WP;

import Shared.WaitPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by kruzhitskaya on 31.03.15.
 */
public class TestLogInLogOutAbandonedCartWP {

    private WebDriver driver;
    private String baseUrl = "http://triggmine-02.videal.net/";

    @BeforeTest
    public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().deleteAllCookies();
        driver.get(baseUrl);

    }

    @Test
    public void testLogInLogOutAbandonedCart() throws InterruptedException {

        LoginLogoutWpPage.loginAction("Kate", "0508101626", driver);//log in

        String cartItemsAmountXpath = ".//*[@id='sliding_cart']/div/table/tfoot/tr[1]/td[1]";
        WaitPage.waitElementLocated(cartItemsAmountXpath, driver);

        if (driver.findElement(By.xpath(cartItemsAmountXpath)).isDisplayed())//check if cart items >0
        {
            PurchaseWpPage.purchaseLoginUser(driver);//perform a purchase
        }

        AddDeleteItemsWpPage.clickLogo(driver);//go to the Home page

        AddDeleteItemsWpPage.addItem(driver);//add item

        LoginLogoutWpPage.logOutAction(driver);//log out

        AddDeleteItemsWpPage.addItem(driver);//add item

        Assert.assertEquals("1 item", driver.findElement(By.xpath(cartItemsAmountXpath)).getText());//check cart item amount = 1

    }

    @AfterTest
    public void tearDown() {
        //driver.quit();
    }
}
