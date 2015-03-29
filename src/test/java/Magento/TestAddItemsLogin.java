package Magento;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by kruzhitskaya on 30.03.15.
 */
public class TestAddItemsLogin {
    private WebDriver driver;
    private String baseURL = "http://triggmine-05.videal.net/";

    @BeforeTest
    public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().deleteAllCookies();
        driver.get(baseURL+"/index.php/plat-ja-ot-olega/plat-ja-s-dlinnym-rukavom.html");
    }

    @Test
    public void testAddItemsLogin() {

        AddDeleteItemsPage.addItem(driver);//add item to the cart

        LoginLogoutPage.logInAction("triggmine01@gmail.com", "0508101626", driver);//log in

    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }


}
