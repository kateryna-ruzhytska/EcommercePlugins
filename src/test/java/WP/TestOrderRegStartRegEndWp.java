package WP;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by kruzhitskaya on 02.04.15.
 */
public class TestOrderRegStartRegEndWp {
    private WebDriver driver;
    private String baseUrl = "http://triggmine-02.videal.net/";

    @BeforeTest
    public void setUp() {
        driver = new FirefoxDriver();
        driver.get(baseUrl);
        driver.manage().deleteAllCookies();
    }

    @Test(priority = 1)
    public void testRegStart() {

        AddDeleteItemsWpPage.addItem(driver);//add items
        PurchaseWpPage.clickCheckoutLabel(driver);//click Checkout
        BillingWpPage.setBillingInf("Kate","Test","Test", "Test","Test", "Test", driver);//set billing inf
        BillingWpPage.selectCountry(driver);//select Country
        BillingWpPage.setRandomEmail(driver);//set random email
        PurchaseWpPage.clickPurchaseButton(driver);//click Purchase button

    }

    @Test(priority = 2)
    public void testRegEnd() {

        AddDeleteItemsWpPage.clickLogo(driver);//click Logo
        AddDeleteItemsWpPage.addItem(driver);//add items
        PurchaseWpPage.clickCheckoutLabel(driver);//click Checkout
        BillingWpPage.setBillingInf("Kate", "Test", "Test", "Test", "Test", "Test", driver);//set billing inf
        BillingWpPage.selectCountry(driver);//select Country
        BillingWpPage.setEmail("triggmine01@gmail.com", driver);//set email
        PurchaseWpPage.clickPurchaseButton(driver);//click Purchase button

    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
