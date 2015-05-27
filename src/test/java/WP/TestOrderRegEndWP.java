package WP;

import Shared.LogParserPage;
import com.jcraft.jsch.SftpException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by kruzhitskaya on 27.05.15.
 */
public class TestOrderRegEndWP {
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
    public void testAddItem() throws InterruptedException, SftpException, IOException, ParseException {
        LogParserPage.removeFile(filePathWp);//remove log.txt

        AddDeleteItemsWpPage.addItem(driver);//add items

//CreateReplaceCartItem
        ArrayList<String> json = LogParserPage.readFile(filePathWp);
        for (int i = 0; i < json.size(); i++) {
            jsonObject = LogParserPage.getJson(json.get(i));
            jsonObjectHashMap = (HashMap) jsonObject.get("Request");
            if (jsonObjectHashMap.get("Method").toString().contentEquals("CreateReplaceCartItem")) {
                break;
            }
        }
        jsonObjectHashMap = (HashMap) jsonObject.get("Request");
        token = (String) jsonObjectHashMap.get("Token");//check Token is the same for each action
        assertEquals(jsonObjectHashMap.get("Method"), "CreateReplaceCartItem");//check proper method is sent

        jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        JSONObject data = (JSONObject) jsonObjectHashMap.get("Data");
        buyerId = (String) data.get("BuyerId");//get BuyerId
        cartId = (String) data.get("CartId");//getCartId
        assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"

    }

    @Test(priority = 2)
    public void testRegEnd() throws InterruptedException, SftpException, IOException, ParseException {
        LogParserPage.removeFile(filePathWp);//remove log.txt

//fill out register form
        PurchaseWpPage.clickCheckoutLabel(driver);//click Checkout
        BillingWpPage.setEmail("triggmine01@gmail.com", driver);//set email
        BillingWpPage.setBillingInf("Kate", "Test", "Test", "Test", "Test", "Test", driver);//set billing inf
        BillingWpPage.selectCountry(driver);//select Country

        ArrayList<String> json = LogParserPage.readFile(filePathWp);
//GetBuyerId
        for (int i = 0; i < json.size(); i++) {
            jsonObject = LogParserPage.getJson(json.get(i));
            jsonObjectHashMap = (HashMap) jsonObject.get("Request");
            JSONObject data = (JSONObject) jsonObjectHashMap.get("Data");
            if ( (jsonObjectHashMap.get("Method").toString().contentEquals("GetBuyerId"))
            && (data.get("BuyerEmail").toString().contentEquals("triggmine01@gmail.com")) ) {
                break;
            }
        }
        jsonObjectHashMap = (HashMap) jsonObject.get("Request");
        JSONObject data = (JSONObject) jsonObjectHashMap.get("Data");
        assertEquals(buyerId, data.get("BuyerId"));//check "BuyerId"
        assertEquals(token, jsonObjectHashMap.get("Token"));//check Token is the same for each action
        assertEquals(jsonObjectHashMap.get("Method"), "GetBuyerId");//check proper method is sent

        jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"

//CreateReplaceBuyerInfo
        for (int i = 0; i < json.size(); i++) {
            jsonObject = LogParserPage.getJson(json.get(i));
            jsonObjectHashMap = (HashMap) jsonObject.get("Request");
            if ( (jsonObjectHashMap.get("Method").toString().contentEquals("CreateReplaceBuyerInfo"))
                    && (data.get("BuyerEmail").toString().contentEquals("triggmine01@gmail.com")) ) {
                break;
            }
        }
        jsonObjectHashMap = (HashMap) jsonObject.get("Request");
        data = (JSONObject) jsonObjectHashMap.get("Data");
        assertEquals(buyerId, data.get("BuyerId"));//check "BuyerId"
        assertTrue(data.containsKey("BuyerRegEnd"));
        assertEquals(token, jsonObjectHashMap.get("Token"));//check Token is the same for each action
        assertEquals(jsonObjectHashMap.get("Method"), "CreateReplaceBuyerInfo");//check proper method is sent

        jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"

//CreateReplaceCart
        for (int i = 0; i < json.size(); i++) {
            jsonObject = LogParserPage.getJson(json.get(i));
            jsonObjectHashMap = (HashMap) jsonObject.get("Request");
            if (jsonObjectHashMap.get("Method").toString().contentEquals("CreateReplaceCart")) {
                break;
            }
        }
        jsonObjectHashMap = (HashMap) jsonObject.get("Request");
        data = (JSONObject) jsonObjectHashMap.get("Data");
        Assert.assertEquals(buyerId, data.get("BuyerId"));//check "BuyerId"
        Assert.assertEquals(cartId, data.get("CartId"));//check "CartId"
        Assert.assertEquals(token, jsonObjectHashMap.get("Token"));//check Token is the same for each action
        Assert.assertEquals(jsonObjectHashMap.get("Method"), "CreateReplaceCart");//check proper method is sent

        jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        Assert.assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"


//perform a purchase
        LogParserPage.removeFile(filePathWp);//remove log.txt
        PurchaseWpPage.clickPurchaseButton(driver);//click Purchase button

        json = LogParserPage.readFile(filePathWp);//read json

//GetBuyerId
        for (int i = 0; i < json.size(); i++) {
            jsonObject = LogParserPage.getJson(json.get(i));
            jsonObjectHashMap = (HashMap) jsonObject.get("Request");
            if (jsonObjectHashMap.get("Method").toString().contentEquals("GetBuyerId")) {
                break;
            }
        }
        jsonObjectHashMap = (HashMap) jsonObject.get("Request");
        data = (JSONObject) jsonObjectHashMap.get("Data");
        assertEquals(buyerId, data.get("BuyerId"));//check "BuyerId"
        assertEquals(token, jsonObjectHashMap.get("Token"));//check Token is the same for each action
        assertEquals(jsonObjectHashMap.get("Method"), "GetBuyerId");//check proper method is sent

        jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"

//CreateReplaceBuyerInfo
        for (int i = 0; i < json.size(); i++) {
            jsonObject = LogParserPage.getJson(json.get(i));
            jsonObjectHashMap = (HashMap) jsonObject.get("Request");
            if (jsonObjectHashMap.get("Method").toString().contentEquals("CreateReplaceBuyerInfo")) {
                break;
            }
        }
        jsonObjectHashMap = (HashMap) jsonObject.get("Request");
        data = (JSONObject) jsonObjectHashMap.get("Data");
        assertEquals(buyerId, data.get("BuyerId"));//check "BuyerId"
        assertTrue(data.containsKey("BuyerRegEnd"));
        assertEquals(data.get("FirstName").toString(), "Kate");
        assertEquals(data.get("LastName").toString(), "Test");
        assertEquals(token, jsonObjectHashMap.get("Token"));//check Token is the same for each action
        assertEquals(jsonObjectHashMap.get("Method"), "CreateReplaceBuyerInfo");//check proper method is sent

        jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"

//CreateReplaceCart
        for (int i = 0; i < json.size(); i++) {
            jsonObject = LogParserPage.getJson(json.get(i));
            jsonObjectHashMap = (HashMap) jsonObject.get("Request");
            if (jsonObjectHashMap.get("Method").toString().contentEquals("CreateReplaceCart")) {
                break;
            }
        }
        jsonObjectHashMap = (HashMap) jsonObject.get("Request");
        data = (JSONObject) jsonObjectHashMap.get("Data");
        Assert.assertEquals(buyerId, data.get("BuyerId"));//check "BuyerId"
        Assert.assertEquals(cartId, data.get("CartId"));//check "CartId"
        Assert.assertEquals(token, jsonObjectHashMap.get("Token"));//check Token is the same for each action
        Assert.assertEquals(jsonObjectHashMap.get("Method"), "CreateReplaceCart");//check proper method is sent

        jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        Assert.assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"

//PurchaseCart
        for (int i = 0; i < json.size(); i++) {
            jsonObject = LogParserPage.getJson(json.get(i));
            jsonObjectHashMap = (HashMap) jsonObject.get("Request");
            if (jsonObjectHashMap.get("Method").toString().contentEquals("PurchaseCart")) {
                break;
            }
        }
        jsonObjectHashMap = (HashMap) jsonObject.get("Request");
        data = (JSONObject) jsonObjectHashMap.get("Data");
        assertEquals(buyerId, data.get("BuyerId"));//check "BuyerId"
        Assert.assertEquals(cartId, data.get("CartId"));//check "CartId"
        assertEquals(token, jsonObjectHashMap.get("Token"));//check Token is the same for each action
        assertEquals(jsonObjectHashMap.get("Method"), "PurchaseCart");//check proper method is sent

        jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"

    }

}
