package Magento;

import Shared.GenerateDataPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by kruzhitskaya on 02.03.15.
 */
public class TestOrderRegStartRegEndMgn {

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

        AddDeleteItemsMgnPage.addItem(driver);//add item to the cart

        PurchaseMgnPage.guestCheckout(driver);//Checkout as guest

        BillingMgnPage.fillBilling("test", "test", "test 2", "test",
                "12345", "8005558789", driver); //fill main billing inf

        BillingMgnPage.chooseStateProvince(driver);// choose State/Province

        BillingMgnPage.generateNewEmail(driver);//generate random email

        PurchaseMgnPage.purchaseRegistrationGuest(driver);
    }

    @Test(priority = 2)
    public void testOrderRegEnd() {
        driver.manage().deleteAllCookies();
        driver.get(baseUrl + "/index.php/plat-ja-ot-olega/plat-ja-s-dlinnym-rukavom.html");

        AddDeleteItemsMgnPage.addItem(driver);//add item to the cart

        PurchaseMgnPage.guestCheckout(driver);//Checkout as guest

        BillingMgnPage.fillBilling("test", "test", "test 2", "test",
                "12345", "8005558789", driver); //fill main billing inf

        BillingMgnPage.chooseStateProvince(driver);// choose State/Province

        BillingMgnPage.enterEmail("triggmine01@gmail.com", driver);

        PurchaseMgnPage.purchaseRegistrationGuest(driver);

    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
