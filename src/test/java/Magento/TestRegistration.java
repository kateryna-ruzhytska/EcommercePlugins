package Magento;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by kruzhitskaya on 27.03.15.
 */
public class TestRegistration {
    private WebDriver driver;
    private String baseUrl = "http://triggmine-05.videal.net/";
    private GenerateDataPage genData;

    @BeforeTest
    public void setUp() {
        driver = new FirefoxDriver();
        genData = new GenerateDataPage();
    }

    @Test
    public void testRegistrationAbandonedCart() {
        driver.get(baseUrl + "/index.php/plat-ja-ot-olega/plat-ja-s-dlinnym-rukavom.html");

        By addItemButtonXpath = By.xpath(".//*[@id='top']/body/div[1]/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[1]/div/div[2]/button");
        driver.findElement(addItemButtonXpath).click();//add item to the cart

        By cartLabelXpath = By.xpath(".//*[@id='header']/div/div[2]/div/div/a/span[2]");
        driver.findElement(cartLabelXpath).click();
        ; //view cart options

        String checkoutButtonXpath = ".//*[@id='header-cart']/div[3]/div[4]/ul/li/a";
        WaitPage.waitElementClickable(checkoutButtonXpath, driver);
        driver.findElement(By.xpath(checkoutButtonXpath)).click(); //checkout

        String registerAndCheckoutXpath = ".//*[@id='checkout-step-login']/div/div[1]/ul/li[2]/label";
        WaitPage.waitElementClickable(registerAndCheckoutXpath, driver);
        driver.findElement(By.xpath(registerAndCheckoutXpath)).click(); // register and checkout radio button

        String guestRegisterContinueButtonXpath = ".//*[@id='onepage-guest-register-button']";
        WaitPage.waitElementLocated(guestRegisterContinueButtonXpath, driver);
        driver.findElement(By.xpath(guestRegisterContinueButtonXpath)).click(); //guest register button

        String billingFormXpath = ".//*[@id='checkout-step-billing']";
        WaitPage.waitElementLocated(billingFormXpath, driver);
        Assert.assertTrue(driver.findElement(By.xpath(billingFormXpath)).isDisplayed());

        BillingInformationPage.fillBilling("test", "test", /*"triggmine01@gmail.com",*/ "test 2", "test", "12345", "8005558789", driver); //fill billing inf

        String stateProvinceXpath = ".//*[@id='billing:region_id']";
        driver.findElement(By.xpath(stateProvinceXpath)).click(); //select State/Province
        final Select select = new Select (driver.findElement(By.xpath(stateProvinceXpath)));
        final int optionIndex = 3;
        WebElement optionThird = select.getOptions().get(optionIndex);
        optionThird.click();

        driver.findElement(By.xpath(".//*[@id='billing:email']")).clear();
        driver.findElement(By.xpath(".//*[@id='billing:email']")).sendKeys(genData.generateEmail(30));//generate random email

        By passwordXpath = By.xpath(".//*[@id='billing:customer_password']");
        driver.findElement(passwordXpath).clear();
        driver.findElement(passwordXpath).sendKeys("0508101626");

        By confirmPasswordXpath = By.xpath(".//*[@id='billing:confirm_password']");
        driver.findElement(confirmPasswordXpath).clear();
        driver.findElement(confirmPasswordXpath).sendKeys("0508101626");

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

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}

