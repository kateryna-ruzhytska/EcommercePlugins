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
import java.util.HashMap;

/**
 * Created by kruzhitskaya on 31.03.15.
 */
public class TestAddDeleteItemsWp {
    private WebDriver driver;
    private String baseUrl = "http://wordpress.triggmine.videal.net/";
    private String filePathWp = "/home/qa/web/wordpress.triggmine.videal.net/public_html/wp-content/plugins/triggmine/core/logs/log.txt";
    private String buyerId;
    private String cartId;
    private String token;

    @BeforeTest
    public void setUp() {
        driver = new FirefoxDriver();
        driver.get(baseUrl);
        driver.manage().deleteAllCookies();

    }

    @BeforeTest(dependsOnMethods = "setUp")
    public void initBuyer() throws IOException {
        LogParserPage.setConnection(filePathWp);
    }

    @Test(priority = 1)
    public void testAddItem() throws InterruptedException, SftpException, IOException, ParseException {
        LogParserPage.removeFile(filePathWp);
        AddDeleteItemsWpPage.addItem(driver);//add item

        ArrayList<String> json = LogParserPage.readFile(filePathWp);
        JSONObject jsonObject = LogParserPage.getJson(json.get(0));

        HashMap jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        Assert.assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"
        JSONObject data = (JSONObject) jsonObjectHashMap.get("Data");
        buyerId = (String) data.get("BuyerId");//get BuyerId in response
        cartId = (String) data.get("CartId");//get CartId in response

        jsonObjectHashMap = (HashMap) jsonObject.get("Request");
        Assert.assertEquals(jsonObjectHashMap.get("Method"), "CreateReplaceCartItem");//check proper method is sent
        token = (String) (jsonObjectHashMap.get("Token"));//check Token is the same for each action

    }

    @Test(priority = 2)
    public void testUpdateItem() throws IOException, InterruptedException, ParseException, SftpException {
        LogParserPage.removeFile(filePathWp);
        AddDeleteItemsWpPage.addItem(driver);//update cart
        Thread.sleep(5000);

        ArrayList<String> json = LogParserPage.readFile(filePathWp);
        JSONObject jsonObject = LogParserPage.getJson(json.get(0));

        HashMap jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        Assert.assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"

        jsonObjectHashMap = (HashMap) jsonObject.get("Request");
        JSONObject data = (JSONObject) jsonObjectHashMap.get("Data");
        Assert.assertEquals(data.get("ReplaceOnly").toString(), "1");//check item is updated
        Assert.assertEquals(buyerId, data.get("BuyerId"));//check "BuyerId" in the same
        Assert.assertEquals(cartId, data.get("CartId"));//check "CartId" in the same
        Assert.assertEquals(token, jsonObjectHashMap.get("Token"));//check Token is the same for each action
        Assert.assertEquals(jsonObjectHashMap.get("Method"), "CreateReplaceCartItem");//check proper method is sent
    }

    @Test(priority = 3)
    public void testDeleteItem() throws IOException, ParseException, InterruptedException, SftpException {
        LogParserPage.removeFile(filePathWp);
        AddDeleteItemsWpPage.deleteItem(driver);//clear cart

        ArrayList<String> json = LogParserPage.readFile(filePathWp);
        JSONObject jsonObject = LogParserPage.getJson(json.get(0));

        HashMap jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        Assert.assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"

        jsonObjectHashMap = (HashMap) jsonObject.get("Request");
        JSONObject data = (JSONObject) jsonObjectHashMap.get("Data");
        Assert.assertEquals(buyerId, data.get("BuyerId"));//check "BuyerId" in the same
        Assert.assertEquals(cartId, data.get("CartId"));//check "CartId" in the same
        Assert.assertEquals(token, jsonObjectHashMap.get("Token"));//check Token is the same for each action
        Assert.assertEquals(jsonObjectHashMap.get("Method"), "CreateReplaceCart");//check proper method is sent

    }

    @AfterTest
    public void tearDown() throws Exception {
        driver.quit();
    }
}
