package WP;

import Shared.LogParserPage;
import com.jcraft.jsch.SftpException;
import junit.framework.Assert;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by kruzhitskaya on 31.03.15.
 */
public class TestAddDeleteItemsWp {
    private WebDriver driver;
    private String baseUrl = "http://wordpress.triggmine.videal.net/";
    private String filePathWp = "/home/qa/web/wordpress.triggmine.videal.net/public_html/wp-content/plugins/triggmine/core/logs/log.txt";
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

    @BeforeTest(dependsOnMethods = "setUp")
    public void initBuyer() throws IOException, ParseException, InterruptedException, SftpException {
        LogParserPage.setConnection(filePathWp);
        LogParserPage.removeFile(filePathWp);

        AddDeleteItemsWpPage.addItem(driver);//add item
        driver.navigate().refresh();

        ArrayList<String> json = LogParserPage.readFile(filePathWp);
        JSONObject jsonObject = LogParserPage.getJson(json.get(0));
        Assert.assertEquals(jsonObject.get("ErrorCode").toString(), "0");

        buyerId = (String) jsonObject.get("buyerId");
    }

    @Test(priority = 1)
    public void testUpdateItem() throws IOException, InterruptedException, ParseException {
        AddDeleteItemsWpPage.addItem(driver);//update cart
        Thread.sleep(5000);

        ArrayList<String> json = LogParserPage.readFile(filePathWp);
        JSONObject jsonObject = LogParserPage.getJson(json.get(0));

        Assert.assertEquals(jsonObject.get("ErrorCode").toString(), "0");

        Assert.assertEquals(buyerId, jsonObject.get("buyerId"));
    }

    @Test(priority = 2)
    public void testDeleteItem() throws IOException, ParseException {
        AddDeleteItemsWpPage.deleteItem(driver);//clear cart

        ArrayList<String> json = LogParserPage.readFile(filePathWp);
        JSONObject jsonObject = LogParserPage.getJson(json.get(0));

        Assert.assertEquals(jsonObject.get("ErrorCode").toString(), "0");

        Assert.assertEquals(buyerId, jsonObject.get("buyerId"));

    }

    @AfterTest
    public void tearDown() throws Exception {
        driver.quit();
    }
}
