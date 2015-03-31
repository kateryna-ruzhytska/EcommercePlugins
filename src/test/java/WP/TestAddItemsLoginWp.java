package WP;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by kruzhitskaya on 31.03.15.
 */
public class TestAddItemsLoginWp {
    private WebDriver driver;
    private String baseUrl = "http://triggmine-02.videal.net/";

    @BeforeTest
    public void setUp() {
        driver = new FirefoxDriver();
        driver.get(baseUrl);
        driver.manage().deleteAllCookies();

    }

    @Test
    public void testAddItemsLogin() {

        AddDeleteItemsWpPage.addItem(driver);//add item

        LoginLogoutWpPage.loginAction("Kate", "0508101626", driver);//log in

    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
