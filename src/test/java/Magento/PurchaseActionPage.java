package Magento;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * Created by kruzhitskaya on 30.03.15.
 */
public class PurchaseActionPage {

    public static void purchaseLoginUser(WebDriver driver) {

        viewCartOptionsAndCheckout(driver);
        continueButtonBilling(driver);
        continueButtonShippingMethod(driver);
        continueButtonPaymentInf(driver);
        placeOrder(driver);
    }
    public static void purchaseRegistrationGuest(WebDriver driver) {

        continueButtonBilling(driver);
        continueButtonShippingMethod(driver);
        continueButtonPaymentInf(driver);
        placeOrder(driver);
    }

    public static void registerAndCheckout(WebDriver driver) {

        viewCartOptionsAndCheckout(driver);

        String registerAndCheckoutXpath = ".//*[@id='checkout-step-login']/div/div[1]/ul/li[2]/label";
        WaitPage.waitElementClickable(registerAndCheckoutXpath, driver);
        driver.findElement(By.xpath(registerAndCheckoutXpath)).click(); // register and checkout radio button

        String guestRegisterContinueButtonXpath = ".//*[@id='onepage-guest-register-button']";
        WaitPage.waitElementLocated(guestRegisterContinueButtonXpath, driver);
        driver.findElement(By.xpath(guestRegisterContinueButtonXpath)).click(); //guest register button

        String billingFormXpath = ".//*[@id='checkout-step-billing']";
        WaitPage.waitElementLocated(billingFormXpath, driver);
        Assert.assertTrue(driver.findElement(By.xpath(billingFormXpath)).isDisplayed());//billing form is displayed
    }

    public static void guestCheckout(WebDriver driver) {

        viewCartOptionsAndCheckout(driver);

        String guestRegisterContinueButtonXpath = ".//*[@id='onepage-guest-register-button']";
        WaitPage.waitElementLocated(guestRegisterContinueButtonXpath, driver);
        driver.findElement(By.xpath(guestRegisterContinueButtonXpath)).click(); //checkout as guest

        String billingFormXpath = ".//*[@id='checkout-step-billing']";
        WaitPage.waitElementLocated(billingFormXpath, driver);
        Assert.assertTrue(driver.findElement(By.xpath(billingFormXpath)).isDisplayed());
    }


    public static void continueButtonBilling(WebDriver driver) {
        String billingButtonContinueXpath = ".//*[@id='billing-buttons-container']/button";
        WaitPage.waitElementClickable(billingButtonContinueXpath, driver);
        driver.findElement(By.xpath(billingButtonContinueXpath)).sendKeys(Keys.ENTER); //Continue button on Billing Inf page
    }
    public static void continueButtonShippingMethod(WebDriver driver) {
        String shippingButtonContinueXpath = ".//*[@id='shipping-method-buttons-container']/button";
        WaitPage.waitElementClickable(shippingButtonContinueXpath, driver);
        driver.findElement(By.xpath(shippingButtonContinueXpath)).sendKeys(Keys.ENTER); //Continue button on Shipping Method page
    }
    public static void continueButtonPaymentInf(WebDriver driver) {
        String paymentButtonContinueXpath = ".//*[@id='payment-buttons-container']/button";
        String checkoutPaymentMethodXpath = ".//*[@id='checkout-payment-method-load']/dt[1]/label";
        WaitPage.waitElementClickable(paymentButtonContinueXpath, driver);
        driver.findElement(By.xpath(checkoutPaymentMethodXpath)).click(); //Check/Money order on Payment Inf page
        driver.findElement(By.xpath(paymentButtonContinueXpath)).sendKeys(Keys.ENTER); //Continue button on Payment Inf page
    }
    public static void placeOrder(WebDriver driver) {
        String reviewButtonContinueXpath = ".//*[@id='review-buttons-container']/button";
        WaitPage.waitElementClickable(reviewButtonContinueXpath, driver);
        driver.findElement(By.xpath(reviewButtonContinueXpath)).sendKeys(Keys.ENTER); //Place order

        String orderConfirmation = ".//*[@id='top']/body/div[1]/div/div[2]/div/div/h2";
        WaitPage.waitElementLocated(orderConfirmation, driver);
        Assert.assertTrue(driver.findElement(By.xpath(orderConfirmation)).isDisplayed());//order confirmation is displayed
    }

    public static void viewCartOptionsAndCheckout(WebDriver driver) {
        By cartLabelXpath = By.xpath(".//*[@id='header']/div/div[2]/div/div/a/span[2]");
        driver.findElement(cartLabelXpath).click();; //view cart options
        By checkoutButtonCss = By.cssSelector(".button.checkout-button");
        driver.findElement(checkoutButtonCss).click(); //checkout

    }

}
