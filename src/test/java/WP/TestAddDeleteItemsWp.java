package WP;

import Shared.LogParserPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by kruzhitskaya on 31.03.15.
 */
public class TestAddDeleteItemsWp {
    private WebDriver driver;
    private String baseUrl = "http://triggmine-02.videal.net/";
    private String filePathWp = "/var/www/wordpress/wordpress/wp-content/plugins/triggmine/core/logs/log.txt";
    //private LogParserPage log = new LogParserPage();
    private String buyerId;
    private String cartId;
    private String createReplaceCartItem;

    @BeforeTest
    public void setUp() {
        driver = new FirefoxDriver();
        driver.get(baseUrl);
        driver.manage().deleteAllCookies();

    }

    @Test(priority = 1)
    public void testAddItem() throws IOException {
        LogParserPage.setConnection(1011, filePathWp);
        AddDeleteItemsWpPage.addItem(driver);//add item
        LogParserPage.readJson(filePathWp, "CartId");


        //Assert.assertNotNull(log);


    }

    @Test(priority = 2)
    public void testUpdateItem() throws IOException, InterruptedException {
        LogParserPage.setConnection(1011, filePathWp);
        AddDeleteItemsWpPage.addItem(driver);//update cart
        Thread.sleep(5000);
        //LogParserPage.readFile(logPathWp);
    }

    @Test(priority = 3)
    public void testDeleteItem() throws IOException {
        LogParserPage.setConnection(1011, filePathWp);
        AddDeleteItemsWpPage.deleteItem(driver);//clear cart
        //LogParserPage.readFile(logPathWp);
    }

    @AfterTest
    public void tearDown() throws Exception {
        driver.quit();
    }
}
