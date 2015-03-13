package Magento;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by kruzhitskaya on 02.03.15.
 */
public class TestOrderRegStartRegEnd {

    private WebDriver driver;
    private String baseUrl = "http://triggmine-05.videal.net/";

    @BeforeTest
    public void setUp() {
        driver = new FirefoxDriver();
        driver.get(baseUrl + "/index.php/plat-ja-ot-olega/plat-ja-s-dlinnym-rukavom.html");
        driver.manage().window().setPosition(new Point(-1, -1));
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
    }

    @Test
    public void testOrderRegStart() throws InterruptedException {

        String addItemButtonXpath = ".//*[@id='top']/body/div[1]/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[1]/div/div[2]/button";
        driver.findElement(By.xpath(addItemButtonXpath))
                .click();  //add item to the cart

        String cartLabelXpath = ".//*[@id='header']/div/div[2]/div/div/a/span[2]";
        driver.findElement(By.xpath(cartLabelXpath)).click(); //view cart options

        String checkoutButtonCss = ".button.checkout-button";
        driver.findElement(By.cssSelector(checkoutButtonCss)).click(); //checkout

        String guestRegisterContinueButtonXpath = ".//*[@id='onepage-guest-register-button']";
        WaitPage.waitElementLocated(guestRegisterContinueButtonXpath, driver);
        driver.findElement(By.xpath(guestRegisterContinueButtonXpath)).click(); //checkout as guest

        String billingFormXpath = ".//*[@id='checkout-step-billing']";
        WaitPage.waitElementLocated(billingFormXpath, driver);
        Assert.assertTrue(driver.findElement(By.xpath(billingFormXpath)).isDisplayed());

        BillingInformationPage.fillBilling("test", "test", "triggmine01@gmail.com", "test 2", "test", "12345", "8005558789", driver); //fill billing inf

        String stateProvinceXpath = ".//*[@id='billing:region_id']";
        driver.findElement(By.xpath(stateProvinceXpath)).click(); //select State/Province
        final Select select = new Select (driver.findElement(By.xpath(stateProvinceXpath)));
        final int optionIndex = 3;
        WebElement optionThird = select.getOptions().get(optionIndex);
//        String optionXpath = stateProvinceXpath + "/option[text()='American Samoa']";
//        WaitPage.waitElementClickable(optionXpath, driver);
        optionThird.click();

        String billingButtonContinueXpath = ".//*[@id='billing-buttons-container']/button";
        WaitPage.waitElementClickable(billingButtonContinueXpath, driver);
        driver.findElement(By.xpath(billingButtonContinueXpath)).sendKeys(Keys.ENTER); //Continue button on Billing Inf page

        String shippingButtonContinueXpath = ".//*[@id='shipping-method-buttons-container']/button";
        WaitPage.waitElementClickable(shippingButtonContinueXpath, driver);
        driver.findElement(By.xpath(shippingButtonContinueXpath)).sendKeys(Keys.ENTER); //Continue button on Shipping Method page

        String paymentButtonContinueXpath = ".//*[@id='payment-buttons-container']/button";
        String checkoutPaymentMethodXpath = ".//*[@id='checkout-payment-method-load']/dt[1]/label";
        WaitPage.waitElementClickable(paymentButtonContinueXpath, driver);
        driver.findElement(By.xpath(checkoutPaymentMethodXpath)).click(); //Check/Money order on Payment Inf page
        driver.findElement(By.xpath(paymentButtonContinueXpath)).sendKeys(Keys.ENTER); //Continue button on Payment Inf page

        String reviewButtonContinueXpath = ".//*[@id='review-buttons-container']/button";
        WaitPage.waitElementClickable(reviewButtonContinueXpath, driver);
        driver.findElement(By.xpath(reviewButtonContinueXpath)).sendKeys(Keys.ENTER); //Place order

        String orderConfirmation = ".//*[@id='top']/body/div[1]/div/div[2]/div/div/h2";
        WaitPage.waitElementLocated(orderConfirmation, driver);
        Assert.assertTrue(driver.findElement(By.xpath(orderConfirmation)).isDisplayed());

    }

    @Test
    public void testOrderRegEnd(){

    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
