package WP;

import Shared.LogParserPage;
import com.jcraft.jsch.SftpException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kruzhitskaya on 31.03.15.
 */
public class TestAddItemsLoginWp {
    private WebDriver driver;
    private String baseUrl = "http://wordpress.triggmine.videal.net/";
    private String filePathWp = "/home/qa/web/wordpress.triggmine.videal.net/public_html/wp-content/plugins/triggmine/core/logs/log.txt";
    private String buyerId;
    private String cartId;
    private String token;
    JSONObject jsonObject;
    HashMap jsonObjectHashMap = null;


    @BeforeTest
    public void setUp() {
        driver = new FirefoxDriver();
        driver.get(baseUrl);
        driver.manage().deleteAllCookies();
    }

    @BeforeTest(dependsOnMethods = "setUp")
    public void setConnection() throws IOException {
        LogParserPage.setConnection(filePathWp);
    }

    @Test(priority = 1)
    public void testAddItemsLogin() throws InterruptedException, SftpException, IOException, ParseException {
        LogParserPage.removeFile(filePathWp);//remove log.txt

        AddDeleteItemsWpPage.addItem(driver);//add item

        ArrayList<String> json= LogParserPage.readFile(filePathWp);
        for (int i=0; i < json.size(); i++){
            jsonObject = LogParserPage.getJson(json.get(i));
            jsonObjectHashMap = (HashMap) jsonObject.get("Request");
            if(jsonObjectHashMap.get("Method").toString().contentEquals("CreateReplaceCartItem"))
            {break;}
        }

        jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        Assert.assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"
        JSONObject data = (JSONObject) jsonObjectHashMap.get("Data");
        buyerId = (String) data.get("BuyerId");//get BuyerId in response
        cartId = (String) data.get("CartId");//get CartId in response

        jsonObjectHashMap = (HashMap) jsonObject.get("Request");
        Assert.assertEquals(jsonObjectHashMap.get("Method"), "CreateReplaceCartItem");//check proper method is sent
        token = (String) jsonObjectHashMap.get("Token");//check Token is the same for each action

    }

    @Test(priority = 2)
    public void logIn() throws InterruptedException, SftpException, IOException, ParseException {
        LogParserPage.removeFile(filePathWp);//remove log.txt

        LoginLogoutWpPage.loginAction("Kate58", "0508101626", driver);//log in

        ArrayList<String> json= LogParserPage.readFile(filePathWp);

//update cart full
        for (int i=0; i < json.size(); i++){
            jsonObject = LogParserPage.getJson(json.get(i));
            jsonObjectHashMap = (HashMap) jsonObject.get("Request");
            if(jsonObjectHashMap.get("Method").toString().contentEquals("CreateReplaceCart"))
            {break;}
        }

        jsonObjectHashMap = (HashMap) jsonObject.get("Request");
        JSONObject data = (JSONObject) jsonObjectHashMap.get("Data");
        Assert.assertEquals(buyerId, data.get("BuyerId"));//check "BuyerId" in the same
        Assert.assertEquals(cartId, data.get("CartId"));//check "CartId" in the same
        Assert.assertEquals(token, jsonObjectHashMap.get("Token"));//check Token is the same for each action
        Assert.assertEquals(jsonObjectHashMap.get("Method"), "CreateReplaceCart");//check proper method is sent

        jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        Assert.assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"

//getBuyerByEmail
        for (int i=0; i < json.size(); i++){
            jsonObject = LogParserPage.getJson(json.get(i));
            jsonObjectHashMap = (HashMap) jsonObject.get("Request");
            if(jsonObjectHashMap.get("Method").toString().contentEquals("GetBuyerId"))
            {break;}
        }

        jsonObjectHashMap = (HashMap) jsonObject.get("Request");
        data = (JSONObject) jsonObjectHashMap.get("Data");
        Assert.assertEquals(buyerId, data.get("BuyerId"));//check "BuyerId" in the same
        Assert.assertEquals(data.get("BuyerEmail").toString(), "triggminefortest+58@gmail.com");//check buyer email
        Assert.assertEquals(token, jsonObjectHashMap.get("Token"));//check Token is the same for each action
        Assert.assertEquals(jsonObjectHashMap.get("Method"), "GetBuyerId");//check proper method is sent

        jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        Assert.assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"

//updateBuyer
        for (int i=0; i < json.size(); i++){
            jsonObject = LogParserPage.getJson(json.get(i));
            jsonObjectHashMap = (HashMap) jsonObject.get("Request");
            if(jsonObjectHashMap.get("Method").toString().contentEquals("CreateReplaceBuyerInfo"))
            {break;}
        }

        jsonObjectHashMap = (HashMap) jsonObject.get("Request");
        data = (JSONObject) jsonObjectHashMap.get("Data");
        Assert.assertEquals(buyerId, data.get("BuyerId"));//check "BuyerId" in the same
        Assert.assertEquals(data.get("BuyerEmail").toString(), "triggminefortest+58@gmail.com");//check buyer email
        Assert.assertEquals(token, jsonObjectHashMap.get("Token"));//check Token is the same for each action
        Assert.assertEquals(jsonObjectHashMap.get("Method"), "CreateReplaceBuyerInfo");//check proper method is sent
        Assert.assertEquals(data.get("BuyerRegEnd").toString(), "2015-05-19 13:05:12");//check the end of registration

        jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        Assert.assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
