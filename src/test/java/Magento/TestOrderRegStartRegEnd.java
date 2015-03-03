package Magento;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.locks.Condition;

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
        WebDriverWait wait = new WebDriverWait(driver, 5);

        driver.findElement(By.xpath(".//*[@id='top']/body/div[1]/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[1]/div/div[2]/button"))
                .click();  //add item to the cart
        driver.findElement(By.xpath(".//*[@id='header']/div/div[2]/div/div/a/span[2]")).click(); //view cart options
        driver.findElement(By.cssSelector(".button.checkout-button")).click(); //checkout
        WebElement waitElement3 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#onepage-guest-register-button")));
        driver.findElement(By.cssSelector("#onepage-guest-register-button")).click(); //checkout as guest
        WebElement waitElement2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#checkout-step-billing")));
        Assert.assertTrue(driver.findElement(By.cssSelector("#checkout-step-billing")).isDisplayed());

        //BillingInformationPage.fillBilling("test", "test", "triggmine01@gmail.com", "test 2", "test", "12345", "8005558789", driver);

        driver.findElement(By.xpath(".//*[@id='billing:region_id']")).click();
        Select select = new Select (driver.findElement(By.tagName("select")));
        select.getAllSelectedOptions();
        select.selectByIndex(2);







    }

    @Test
    public void testOrderRegEnd(){

    }

    @AfterTest
    public void tearDown() {
        //driver.quit();
    }
}
