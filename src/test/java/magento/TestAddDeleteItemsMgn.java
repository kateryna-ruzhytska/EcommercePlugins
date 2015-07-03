package magento;


import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import shared.LogParserPage;
import com.jcraft.jsch.SftpException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.testng.Assert.*;

/**
 * Created by kruzhitskaya on 25.02.15.
 */
public class TestAddDeleteItemsMgn {
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
        //DesiredCapabilities caps = new DesiredCapabilities();
        //caps.setJavascriptEnabled(true);
        //caps.setCapability("takesScreenshot", false);
        //caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/home/kruzhitskaya/Documents/phantomjs-2.0.0/build.sh");
        //driver = new PhantomJSDriver(caps);
        driver = new FirefoxDriver();
        driver.manage().deleteAllCookies();
        driver.get(baseUrl + "/index.php/bags.html");
    }

    @BeforeTest(dependsOnMethods = "setUp")
    public void setConnection () throws IOException {
        LogParserPage.setConnection(filePathMg);
    }

    @Test(priority = 1)
    public void testAddItems() throws InterruptedException, SftpException, IOException, ParseException {
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
    public void testUpdateItems() throws InterruptedException, SftpException, IOException, ParseException {
        LogParserPage.removeFile(filePathMg);//remove log.txt
        driver.get(baseUrl + "/index.php/bags.html");
        AddDeleteItemsMgnPage.addItem(driver);//update cart

        ArrayList<String> json = LogParserPage.readFile(filePathMg);
//CreateReplaceCartItem
        for (int i=0; i < json.size(); i++){
            jsonObject = LogParserPage.getJson(json.get(i));
            jsonObjectHashMap = (HashMap) jsonObject.get("Request");
            if(jsonObjectHashMap.get("Method").toString().contentEquals("CreateReplaceCartItem"))
            {break;}
        }

        jsonObjectHashMap = (HashMap) jsonObject.get("Request");
        JSONObject data = (JSONObject) jsonObjectHashMap.get("Data");
        assertEquals(buyerId, data.get("BuyerId"));//check "BuyerId" is the same
        assertEquals(cartId, data.get("CartId"));//check "CartId" is the same
        assertEquals(token, jsonObjectHashMap.get("Token"));//check Token is the same for each action
        assertEquals(jsonObjectHashMap.get("Method"), "CreateReplaceCartItem");//check proper method is sent

        jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"
    }

    @Test(priority = 3)
    public void testDeleteItems() throws InterruptedException, SftpException, IOException, ParseException {
        LogParserPage.removeFile(filePathMg);//remove log.txt
        AddDeleteItemsMgnPage.deleteItem(driver);//clear cart

        ArrayList<String> json = LogParserPage.readFile(filePathMg);
//CreateReplaceCart
        for (int i=0; i < json.size(); i++){
            jsonObject = LogParserPage.getJson(json.get(i));
            jsonObjectHashMap = (HashMap) jsonObject.get("Request");
            if(jsonObjectHashMap.get("Method").toString().contentEquals("CleanupCart"))
            {break;}
        }

        jsonObjectHashMap = (HashMap) jsonObject.get("Request");
        JSONObject data = (JSONObject) jsonObjectHashMap.get("Data");
        assertEquals(buyerId, data.get("BuyerId"));//check "BuyerId" is the same
        assertEquals(cartId, data.get("CartId"));//check "CartId" is the same
        assertEquals(token, jsonObjectHashMap.get("Token"));//check Token is the same for each action
        assertEquals(jsonObjectHashMap.get("Method"), "CleanupCart");//check proper method is sent

        jsonObjectHashMap = (HashMap) jsonObject.get("Response");
        assertEquals(jsonObjectHashMap.get("ErrorCode").toString(), "0");//check "ErrorCode" is "0"
    }

    @AfterTest(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

}
