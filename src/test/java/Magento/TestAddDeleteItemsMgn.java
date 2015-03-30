package Magento;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by kruzhitskaya on 25.02.15.
 */
public class TestAddDeleteItemsMgn {
    private WebDriver driver;
    private String baseUrl = "http://triggmine-05.videal.net/";

    @BeforeTest
    public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().deleteAllCookies();
        driver.get(baseUrl + "/index.php/plat-ja-ot-olega/plat-ja-s-dlinnym-rukavom.html");
    }

    @Test(priority = 1)
    public void testAddItems() {

        AddDeleteItemsMgnPage.addItem(driver);//add item to the cart

        driver.get(baseUrl + "/index.php/plat-ja-ot-olega/plat-ja-s-dlinnym-rukavom.html");
        AddDeleteItemsMgnPage.addItem(driver);//update cart
    }

    @Test(priority = 2)
    public void testDeleteItems() {

        AddDeleteItemsMgnPage.deleteItem(driver);//clear cart
    }

    @AfterTest
    public void tearDown() {
        //driver.quit();
    }

}
