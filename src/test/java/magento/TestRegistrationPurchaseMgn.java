package magento;

import shared.GenerateDataPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by kruzhitskaya on 27.03.15.
 */
public class TestRegistrationPurchaseMgn {
    private WebDriver driver;
    private String baseUrl = "http://triggmine-05.videal.net/";
    private GenerateDataPage genData;

    @BeforeTest
    public void setUp() {
        driver = new FirefoxDriver();

    }

    @Test
    public void testRegistrationPurchase() {

        driver.get(baseUrl + "/index.php/plat-ja-ot-olega/plat-ja-s-dlinnym-rukavom.html");

        AddDeleteItemsMgnPage.addItem(driver);//add item to the cart

        PurchaseMgnPage.registerAndCheckout(driver);//click Register and Checkout

        BillingMgnPage.fillBilling("test", "test", "test 2", "test",
                "12345", "8005558789", driver); //fill main billing inf

        BillingMgnPage.chooseStateProvince(driver);// choose State/Province

        BillingMgnPage.generateNewEmail(driver);//generate random email

        BillingMgnPage.enterPassConfirmPass("0508101626", driver);//enter password and confirm it

        PurchaseMgnPage.purchaseRegistrationGuest(driver);//perform a purchase

    }

    @AfterTest(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }
}

