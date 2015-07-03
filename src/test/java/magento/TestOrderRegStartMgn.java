package magento;

import com.jcraft.jsch.SftpException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import shared.LogParserPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by kruzhitskaya on 03.07.15.
 */
public class TestOrderRegStartMgn {
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
        driver.manage().deleteAllCookies();
        driver.get(baseUrl + "/index.php/bags.html");
    }

    @BeforeTest(dependsOnMethods = "setUp")
    public void setConnection () throws IOException {
        LogParserPage.setConnection(filePathMg);
    }

    @Test(priority = 1)
    public void testAddItem() throws InterruptedException, SftpException, IOException, ParseException {
        LogParserPage.removeFile(filePathMg);//remove log.txt
        AddDeleteItemsMgnPage.addItem(driver);//add item to the cart

//CreateReplaceCart
        ArrayList<String> json = LogParserPage.readFile(filePathMg);
        for (int i=0; i < json.size(); i++){
            jsonObject = LogParserPage.getJson(json.get(i));
            jsonObjectHashMap = (HashMap) jsonObject.get("Request");
            if(jsonObjectHashMap.get("Method").toString().contentEquals("CreateReplaceCartItem"))
            {break;}
        }

        jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"
        JSONObject data = (JSONObject) jsonObjectHashMap.get("Data");
        buyerId = (String) data.get("BuyerId");//get BuyerId in response
        cartId = (String) data.get("CartId");//get CartId in response

        jsonObjectHashMap = (HashMap) jsonObject.get("Request");
        assertEquals(jsonObjectHashMap.get("Method"), "CreateReplaceCartItem");//check proper method is sent
        token = (String) (jsonObjectHashMap.get("Token"));//check Token is the same for each action

    }

    @Test(priority = 2)
    public void testOrderRegStart() throws InterruptedException, SftpException, ParseException, IOException {
        LogParserPage.removeFile(filePathMg);//remove log.txt
        PurchaseMgnPage.guestCheckout(driver);//Checkout as guest

//fill out register form
        BillingMgnPage.generateNewEmail(driver);//generate random email
        BillingMgnPage.fillBilling("test", "test", "test 2", "test",
                "12345", "8005558789", driver); //fill main billing inf
        BillingMgnPage.chooseStateProvince(driver);// choose State/Province

        ArrayList<String> json = LogParserPage.readFile(filePathMg);
//GetBuyerId
        for (int i = 0; i < json.size(); i++) {
            jsonObject = LogParserPage.getJson(json.get(i));
            jsonObjectHashMap = (HashMap) jsonObject.get("Request");
            JSONObject data = (JSONObject) jsonObjectHashMap.get("Data");
            if ((jsonObjectHashMap.get("Method").toString().contentEquals("GetBuyerId"))) {
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
            if ((jsonObjectHashMap.get("Method").toString().contentEquals("CreateReplaceBuyerInfo"))) {
                break;
            }
        }
        jsonObjectHashMap = (HashMap) jsonObject.get("Request");
        data = (JSONObject) jsonObjectHashMap.get("Data");
        assertEquals(buyerId, data.get("BuyerId"));//check "BuyerId"
        assertTrue(data.containsKey("BuyerRegStart"));//check BuyerRegEnd
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


    }

    @Test(priority = 3)
    public void testPurchase() throws InterruptedException, SftpException, IOException, ParseException {
        LogParserPage.removeFile(filePathMg);//remove log.txt
        PurchaseMgnPage.purchaseRegistrationGuestContinueButtons(driver);//click continue buttons
        PurchaseMgnPage.placeOrder(driver);//click Purchase button

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
        Assert.assertEquals(cartId, data.get("CartId"));//check "CartId"
        assertEquals(token, jsonObjectHashMap.get("Token"));//check Token is the same for each action
        assertEquals(jsonObjectHashMap.get("Method"), "PurchaseCart");//check proper method is sent

        jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"

    }

    @AfterTest(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }
}
