package WP;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by kruzhitskaya on 01.04.15.
 */
public class TestRegistrationPurchaseWp {
    private WebDriver driver;
    private String baseUrl = "http://triggmine-02.videal.net/";

    @BeforeTest
    public void setUp() {
        driver = new FirefoxDriver();
        driver.get(baseUrl);
        driver.manage().deleteAllCookies();

    }

    @Test
    public void testRegistrationPurchase() {

        AddDeleteItemsWpPage.addItem(driver);//add item

        RegistrationWpPage.registration(driver);//register

        RegistrationWpPage.getGmailData(driver);

        //LoginLogoutWpPage.setEmailPass("Kate", "0508101626", driver);
        //RegistrationWpPage.clickLogin(driver);

    }

    @AfterTest
    public void tearDown() {
        //driver.quit();
    }
}
