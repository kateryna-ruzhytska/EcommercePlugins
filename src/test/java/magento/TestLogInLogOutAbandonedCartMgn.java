package magento;

import com.jcraft.jsch.SftpException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import shared.LogParserPage;
import shared.WaitPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

/**
 * Created by kruzhitskaya on 27.03.15.
 */
public class TestLogInLogOutAbandonedCartMgn {
    private WebDriver driver;
    private String baseUrl = "http://magento.triggmine.videal.net";
    private String filePathMg = "/home/qa/web/magento.triggmine.videal.net/public_html/app/code/community/Videal/Triggmine/Model/core/logs/log.txt";
    private String buyerId;
    private String cartId;
    private String token;
    JSONObject jsonObject;
    HashMap jsonObjectHashMap = null;

    @BeforeTest
    public void setUp() {
        driver = new FirefoxDriver();

    }

    @BeforeTest(dependsOnMethods = "setUp")
    public void setConnection () throws IOException {
        LogParserPage.setConnection(filePathMg);
    }

//TODO get new CartId for all carts that don't have NEW State

    @Test(priority = 1)
    public void testLogIn() throws InterruptedException, SftpException, IOException, ParseException {
        driver.get(baseUrl);
        LogParserPage.removeFile(filePathMg);//remove log.txt
        LoginLogoutMgnPage.logInAction("triggmine02@gmail.com", "0508101626", driver);//log in

        ArrayList<String> json = LogParserPage.readFile(filePathMg);

//GetBuyerId
        for(int i=0; i<json.size(); i++){
            jsonObject = LogParserPage.getJson(json.get(i));
            jsonObjectHashMap = (HashMap) jsonObject.get("Request");
            if(jsonObjectHashMap.get("Method").toString().contentEquals("GetBuyerId"))
            {break;}
        }
        jsonObjectHashMap = (HashMap) jsonObject.get("Request");
        JSONObject data = (JSONObject) jsonObjectHashMap.get("Data");
        token = (String) jsonObjectHashMap.get("Token");//get "Token"
        assertEquals(data.get("BuyerEmail").toString(), "triggmine02@gmail.com");//check buyer email
        assertEquals(jsonObjectHashMap.get("Method").toString(), "GetBuyerId");//check proper method is sent

        jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        data = (JSONObject) jsonObjectHashMap.get("Data");
        buyerId = (String) data.get("BuyerId");//get "BuyerId"
        assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"

//CreateReplaceCart
        for(int i=0; i<json.size(); i++){
            jsonObject = LogParserPage.getJson(json.get(i));
            jsonObjectHashMap = (HashMap) jsonObject.get("Request");
            if (jsonObjectHashMap.get("Method").toString().contentEquals("CreateReplaceCart"))
            {break;}
        }

        jsonObjectHashMap = (HashMap) jsonObject.get("Request");
        data = (JSONObject) jsonObjectHashMap.get("Data");
        assertEquals(buyerId, data.get("BuyerId"));//check "BuyerId" is the same
        assertEquals(token, jsonObjectHashMap.get("Token"));//check "Token" is the same
        assertEquals(jsonObjectHashMap.get("Method").toString(), "CreateReplaceCart");//check proper method is sent

        jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        data = (JSONObject) jsonObjectHashMap.get("Data");
        cartId = (String) data.get("CartId");
        assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"

//CreateReplaceBuyerInfo
        for(int i=0; i<json.size(); i++){
            jsonObject = LogParserPage.getJson(json.get(i));
            jsonObjectHashMap = (HashMap) jsonObject.get("Request");
            if(jsonObjectHashMap.get("Method").toString().contentEquals("CreateReplaceBuyerInfo"))
            {break;}
        }

        jsonObjectHashMap = (HashMap) jsonObject.get("Request");
        data = (JSONObject) jsonObjectHashMap.get("Data");
        assertEquals(buyerId, data.get("BuyerId"));//check "BuyerId" is the same
        assertEquals(token, jsonObjectHashMap.get("Token"));//check "Token" is the same
        assertEquals(data.get("BuyerEmail").toString(), "triggmine02@gmail.com");//check buyer email
        assertEquals(data.get("BuyerRegEnd").toString(), "2015-03-27 16:51:30");//check the end of registration
        assertEquals(jsonObjectHashMap.get("Method").toString(), "CreateReplaceBuyerInfo");//check proper method is sent

        jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"
    }

    @Test(priority = 2)
    public void testPurchase() throws InterruptedException, SftpException, ParseException, IOException {
        String cartItemsAmountXpath = ".//*[@id='header']/div/div[2]/div/div/a/span[3]";
        WaitPage.waitElementLocated(cartItemsAmountXpath, driver);

        if (driver.findElement(By.xpath(cartItemsAmountXpath)).isDisplayed())//check if cart items >0
        {
            LogParserPage.removeFile(filePathMg);//remove log.txt
            PurchaseMgnPage.purchaseLoginUser(driver);//perform a purchase

            ArrayList<String> json = LogParserPage.readFile(filePathMg);
//PurchaseCart
            for (int i = 0; i < json.size(); i++) {
                jsonObject = LogParserPage.getJson(json.get(i));
                jsonObjectHashMap = (HashMap) jsonObject.get("Request");
                if (jsonObjectHashMap.get("Method").toString().contentEquals("PurchaseCart")) {
                    break;
                }
            }
            jsonObjectHashMap = (HashMap) jsonObject.get("Request");
            JSONObject data = (JSONObject) jsonObjectHashMap.get("Data");
            assertEquals(buyerId, data.get("BuyerId"));//check "BuyerId"
            assertEquals(cartId, data.get("CartId"));//check "CartId"
            assertEquals(token, jsonObjectHashMap.get("Token"));//check Token is the same for each action
            assertEquals(jsonObjectHashMap.get("Method"), "PurchaseCart");//check proper method is sent

            jsonObjectHashMap = (HashMap) jsonObject.get("Response");
            assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"
        }
    }

    @Test(priority = 3)
    public void testAddItem() throws InterruptedException, SftpException, ParseException, IOException {
        LogParserPage.removeFile(filePathMg);//remove log.txt
        driver.get(baseUrl + "/index.php/bags.html");
        AddDeleteItemsMgnPage.addItem(driver);//add item to the cart

        ArrayList<String> json = LogParserPage.readFile(filePathMg);

//CreateReplaceCartItem
        for (int i = 0; i < json.size(); i++) {
            jsonObject = LogParserPage.getJson(json.get(i));
            jsonObjectHashMap = (HashMap) jsonObject.get("Request");
            if (jsonObjectHashMap.get("Method").toString().contentEquals("CreateReplaceCartItem")) {
                break;
            }
        }

        jsonObjectHashMap = (HashMap) jsonObject.get("Request");
        JSONObject data = (JSONObject) jsonObjectHashMap.get("Data");
        assertEquals(token, jsonObjectHashMap.get("Token"));//check Token is the same for each action
        assertEquals(buyerId, data.get("BuyerId"));//check "BuyerId"
        assertEquals(jsonObjectHashMap.get("Method"), "CreateReplaceCartItem");//check proper method is sent

        jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        data = (JSONObject) jsonObjectHashMap.get("Data");
        assertNotEquals(cartId, data.get("CartId"));//check new "CartId" has been got
        assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"
    }

    @Test(priority = 4)
    public void testLogOutAbandonedCart() throws InterruptedException, SftpException, ParseException, IOException {
        LogParserPage.removeFile(filePathMg);//remove log.txt
        LoginLogoutMgnPage.logOutAction(driver);//log out
        Thread.sleep(2000);
        driver.get(baseUrl + "/index.php/bags.html");
        AddDeleteItemsMgnPage.addItem(driver);//add item to the cart

        ArrayList<String> json = LogParserPage.readFile(filePathMg);

//CreateReplaceCartItem
        for (int i = 0; i < json.size(); i++) {
            jsonObject = LogParserPage.getJson(json.get(i));
            jsonObjectHashMap = (HashMap) jsonObject.get("Request");
            if (jsonObjectHashMap.get("Method").toString().contentEquals("CreateReplaceCartItem")) {
                break;
            }
        }

        jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"
        JSONObject data = (JSONObject) jsonObjectHashMap.get("Data");
        assertNotEquals(buyerId, data.get("BuyerId"));//check "BuyerId"
        assertNotEquals(cartId, data.get("CartId"));//check "CartId"

        jsonObjectHashMap = (HashMap) jsonObject.get("Request");
        assertEquals(jsonObjectHashMap.get("Method"), "CreateReplaceCartItem");//check proper method is sent
        assertEquals(token, jsonObjectHashMap.get("Token"));//check Token is the same for each action

    }

    @AfterTest(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

}
