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

        LoginLogoutWpPage.LoginAction("Kate", "0508101626", driver);

        driver.get(baseUrl);
        String homePage = ".//*[@id='masthead']";
        WaitPage.waitElementLocated(homePage, driver);//check user is on the Home page

        String welcome = ".//*[@id='wp-admin-bar-my-account']/a";
        Assert.assertEquals("How are you, Kate?", driver.findElement(By.xpath(welcome)).getText());//check user is logged in

    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
