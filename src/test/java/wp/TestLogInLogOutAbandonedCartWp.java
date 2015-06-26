package wp;

import shared.LogParserPage;
import com.jcraft.jsch.SftpException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

/**
 * Created by kruzhitskaya on 31.03.15.
 */
public class TestLogInLogOutAbandonedCartWp {
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
        driver.manage().deleteAllCookies();
        driver.get(baseUrl);

    }

    @BeforeTest(dependsOnMethods = "setUp")
    public void setConnection() throws IOException {
        LogParserPage.setConnection(filePathWp);
    }

    @Test(priority = 1)
    public void testLogIn() throws InterruptedException, SftpException, IOException, ParseException {
        LogParserPage.removeFile(filePathWp);//remove log.txt
        LoginLogoutWpPage.loginAction("Kate59", "0508101626", driver);//log in

        ArrayList<String> json = LogParserPage.readFile(filePathWp);
//GetBuyerId
        for (int i = 0; i < json.size(); i++) {
            jsonObject = LogParserPage.getJson(json.get(i));
            jsonObjectHashMap = (HashMap) jsonObject.get("Request");
            if (jsonObjectHashMap.get("Method").toString().contentEquals("GetBuyerId")) {
                break;
            }
        }

        jsonObjectHashMap = (HashMap) jsonObject.get("Request");
        JSONObject data = (JSONObject) jsonObjectHashMap.get("Data");
        assertEquals(data.get("BuyerEmail").toString(), "triggminefortest+59@gmail.com");//check buyer email
        token = (String) jsonObjectHashMap.get("Token");//get Token
        assertEquals(jsonObjectHashMap.get("Method"), "GetBuyerId");//check proper method is sent

        jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        data = (JSONObject) jsonObjectHashMap.get("Data");
        buyerId = (String) data.get("BuyerId");//get BuyerId
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
        assertEquals(buyerId, data.get("BuyerId"));//check "BuyerId" in the same
        assertEquals(data.get("BuyerEmail").toString(), "triggminefortest+58@gmail.com");//check buyer email
        assertEquals(token, jsonObjectHashMap.get("Token"));//check Token is the same for each action
        assertEquals(jsonObjectHashMap.get("Method"), "CreateReplaceBuyerInfo");//check proper method is sent
        assertEquals(data.get("BuyerRegEnd").toString(), "2015-05-19 13:05:12");//check the end of registration

        jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"

//CreateReplaceCart
        {
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
            Assert.assertEquals(token, jsonObjectHashMap.get("Token"));//check Token is the same for each action
            Assert.assertEquals(jsonObjectHashMap.get("Method"), "CreateReplaceCart");//check proper method is sent

            jsonObjectHashMap = (HashMap) jsonObject.get("Response");
            data = (JSONObject) jsonObjectHashMap.get("Data");
            cartId = (String) data.get("CartId");//get "CartId"
            Assert.assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"
        }
    }

    @Test(priority = 2)
    public void testPurchase() throws InterruptedException, SftpException, IOException, ParseException {
        String shoppingCartCss = ".shoppingcart";
        //WaitPage.waitElementLocated(cartItemsAmountXpath, driver);
        if (driver.findElement(By.cssSelector(shoppingCartCss)).isEnabled())  //check if cart items >0
        {
            LogParserPage.removeFile(filePathWp);//remove log.txt

            PurchaseWpPage.purchaseLoginUser(driver);//perform a purchase
            ArrayList<String> json = LogParserPage.readFile(filePathWp);
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
            data = (JSONObject) jsonObjectHashMap.get("Data");
            assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"
        }
        AddDeleteItemsWpPage.clickLogo(driver);//go to the Home page

    }

    @Test(priority = 3)
    public void testAddItem() throws InterruptedException, SftpException, IOException, ParseException {
        LogParserPage.removeFile(filePathWp);//remove log.txt
        AddDeleteItemsWpPage.addItem(driver);//add item

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
        JSONObject data = (JSONObject) jsonObjectHashMap.get("Data");
        assertEquals(token, jsonObjectHashMap.get("Token"));//check Token is the same for each action
        assertEquals(buyerId, data.get("BuyerId"));//check "BuyerId"
        assertEquals(jsonObjectHashMap.get("Method"), "CreateReplaceCartItem");//check proper method is sent

        jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        data = (JSONObject) jsonObjectHashMap.get("Data");
        assertNotEquals(cartId, data.get("CartId"));//check new "CartId" has been got
        assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"
    }

    @Test(priority = 3)
    public void testLogOutAbandonedCart() throws InterruptedException, SftpException, IOException, ParseException {
        LogParserPage.removeFile(filePathWp);//remove log.txt
        LoginLogoutWpPage.logOutAction(driver);//log out
        AddDeleteItemsWpPage.addItem(driver);//add item

//CreateReplaceCartItem
        ArrayList<String> json = LogParserPage.readFile(filePathWp);
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
        token = (String) jsonObjectHashMap.get("Token");//check Token is the same for each action

        String cartItemsAmountXpath = ".//*[@id='sliding_cart']/div/table/tfoot/tr[1]/td[1]";
        assertEquals("1 item", driver.findElement(By.xpath(cartItemsAmountXpath)).getText());//check cart item amount = 1
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
