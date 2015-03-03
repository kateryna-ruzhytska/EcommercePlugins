package Magento;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

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
    }

    @Test
    public void testOrderRegStart(){
        driver.findElement(By.xpath(".//*[@id='top']/body/div[1]/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[1]/div/div[2]/button"))
                .click();  //add item to the cart
        driver.findElement(By.xpath(".//*[@id='header']/div/div[2]/div/div/a/span[2]")).click(); //view cart options
        driver.findElement(By.cssSelector(".button.checkout-button")).click(); //checkout

        WaitPage.waitElement(".//*[@id='onepage-guest-register-button']", driver);
        driver.findElement(By.cssSelector("#onepage-guest-register-button")).click(); //checkout as guest

        WaitPage.waitElement(".//*[@id='checkout-step-billing']", driver);
        Assert.assertTrue(driver.findElement(By.cssSelector("#checkout-step-billing")).isDisplayed());

        BillingInformationPage.fillBilling("test", "test", "triggmine01@gmail.com", "test 2", "test", "12345", "8005558789", driver); //fill billing inf

        driver.findElement(By.xpath(".//*[@id='billing:region_id']")).click();

        Select select = new Select (driver.findElement(By.tagName("select")));
        select.selectByIndex(3); //select State/Province

        driver.findElement(By.xpath(".//*[@id='billing-buttons-container']/button")) .click(); //Continue button on Billing Inf page

        WaitPage.waitElement(".//*[@id='shipping-method-buttons-container']/button", driver);
        driver.findElement(By.xpath(".//*[@id='shipping-method-buttons-container']/button")) .click(); //Continue button on Shipping Method page

        WaitPage.waitElement(".//*[@id='payment-buttons-container']/button", driver);
        driver.findElement(By.xpath(".//*[@id='checkout-payment-method-load']/dt[1]/label")) .click(); //Check/Money order on Payment Inf page
        driver.findElement(By.xpath(".//*[@id='payment-buttons-container']/button")) .click(); //Continue button on Payment Inf page

        WaitPage.waitElement(".//*[@id='review-buttons-container']/button", driver);
        driver.findElement(By.xpath(".//*[@id='review-buttons-container']/button")) .click(); //Place order

        WaitPage.waitElement(".//*[@id='top']/body/div[1]/div/div[2]/div/div/h2", driver);
        Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='top']/body/div[1]/div/div[2]/div/div/h2")).isDisplayed());


    }

    @Test
    public void testOrderRegEnd(){

    }

    @AfterTest
    public void tearDown() {
        //driver.quit();
    }
}
