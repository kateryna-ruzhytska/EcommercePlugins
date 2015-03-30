package WP;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by kruzhitskaya on 31.03.15.
 */
public class TestAddDeleteItemsWp {
    private WebDriver driver;
    private String baseUrl = "http://triggmine-02.videal.net/";

    @BeforeTest
    public void setUp() {
        driver = new FirefoxDriver();
        driver.get(baseUrl);
        driver.manage().deleteAllCookies();

    }

    @Test(priority = 1)
    public void testAddItem() {

        AddDeleteItemsWpPage.addItem(driver);//add item

        AddDeleteItemsWpPage.addItem(driver);//update cart

    }

    @Test(priority = 2)
    public void testDeleteItem() {

        AddDeleteItemsWpPage.deleteItem(driver);//clear cart

    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
