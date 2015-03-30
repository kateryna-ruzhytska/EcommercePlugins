package Magento;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by kruzhitskaya on 02.03.15.
 */
public class TestOrderRegStartRegEnd {

    private WebDriver driver;
    private String baseUrl = "http://triggmine-05.videal.net/";
    private GenerateDataPage genData;

    @BeforeTest
    public void setUp() {
        driver = new FirefoxDriver();
        genData = new GenerateDataPage();
    }

    @Test(priority = 1)
    public void testOrderRegStart() {
        driver.get(baseUrl + "/index.php/plat-ja-ot-olega/plat-ja-s-dlinnym-rukavom.html");

        AddDeleteItemsPage.addItem(driver);//add item to the cart

        PurchasePage.guestCheckout(driver);//Checkout as guest

        BillingPage.fillBilling("test", "test", "test 2", "test",
                "12345", "8005558789", driver); //fill main billing inf

        BillingPage.chooseStateProvince(driver);// choose State/Province

        BillingPage.generateNewEmail(driver);//generate random email

        PurchasePage.purchaseRegistrationGuest(driver);
    }

    @Test(priority = 2)
    public void testOrderRegEnd() {
        driver.manage().deleteAllCookies();
        driver.get(baseUrl + "/index.php/plat-ja-ot-olega/plat-ja-s-dlinnym-rukavom.html");

        AddDeleteItemsPage.addItem(driver);//add item to the cart

        PurchasePage.guestCheckout(driver);//Checkout as guest

        BillingPage.fillBilling("test", "test", "test 2", "test",
                "12345", "8005558789", driver); //fill main billing inf

        BillingPage.chooseStateProvince(driver);// choose State/Province

        BillingPage.enterEmail("triggmine01@gmail.com", driver);

        PurchasePage.purchaseRegistrationGuest(driver);

    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
