package magento;

import shared.WaitPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by kruzhitskaya on 27.03.15.
 */
public class TestLogInLogOutAbandonedCartMgn {
    private WebDriver driver;
    private String baseUrl = "http://triggmine-05.videal.net/";

    @BeforeTest
    public void setUp() {
        driver = new FirefoxDriver();

    }

    @Test
    public void testLogInLogOutAbandonedCart (){
        driver.get(baseUrl);

        LoginLogoutMgnPage.logInAction("triggmine02@gmail.com", "0508101626", driver);//log in

        String cartItemsAmountXpath = ".//*[@id='header']/div/div[2]/div/div/a/span[3]";
        WaitPage.waitElementLocated(cartItemsAmountXpath, driver);

//TODO get new CartId for all carts that don't have NEW State

        if (driver.findElement(By.xpath(cartItemsAmountXpath)).isDisplayed())//check if cart items >0
        {
            PurchaseMgnPage.purchaseLoginUser(driver);//perform a purchase
        }
            driver.get(baseUrl + "/index.php/plat-ja-ot-olega/plat-ja-s-dlinnym-rukavom.html");

            AddDeleteItemsMgnPage.addItem(driver);//add item to the cart

            LoginLogoutMgnPage.logOutAction(driver);//log out

            AddDeleteItemsMgnPage.addItem(driver);//add item to the cart

    }

    @AfterTest(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

}
