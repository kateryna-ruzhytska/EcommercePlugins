package Magento;

import Shared.WaitPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by kruzhitskaya on 27.03.15.
 */
public class TestLogInLogOutAbandonedCart {
    private WebDriver driver;
    private String baseUrl = "http://triggmine-05.videal.net/";

    @BeforeTest
    public void setUp() {
        driver = new FirefoxDriver();

    }

    @Test
    public void testLogInLogOutAbandonedCart (){
        driver.get(baseUrl);

        LoginLogoutPage.logInAction("triggmine02@gmail.com", "0508101626", driver);//log in

        String cartItemsAmountXpath = ".//*[@id='header']/div/div[2]/div/div/a/span[3]";
        WaitPage.waitElementLocated(cartItemsAmountXpath, driver);

        if (driver.findElement(By.xpath(cartItemsAmountXpath)).isDisplayed())//check if cart items >0
        {
            PurchasePage.purchaseLoginUser(driver);//perform a purchase
        }
            driver.get(baseUrl + "/index.php/plat-ja-ot-olega/plat-ja-s-dlinnym-rukavom.html");

            AddDeleteItemsPage.addItem(driver);//add item to the cart

            LoginLogoutPage.logOutAction(driver);//log out

    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
