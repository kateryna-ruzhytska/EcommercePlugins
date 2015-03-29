package Magento;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by kruzhitskaya on 25.02.15.
 */
public class TestAddDeleteItems {
    private WebDriver driver;
    private String baseUrl = "http://triggmine-05.videal.net/";

    @BeforeTest
    public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().deleteAllCookies();
        driver.get(baseUrl + "/index.php/plat-ja-ot-olega/plat-ja-s-dlinnym-rukavom.html");
    }

    @Test(priority = 1)
    public void testAddItems() {

        By addItemButtonXpath = By.xpath(".//*[@id='top']/body/div[1]/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[1]/div/div[2]/button");
        driver.findElement(addItemButtonXpath).click();//add item to the cart

        String cartItemsAmountXpath = ".//*[@id='header']/div/div[2]/div/div/a/span[3]";
        WaitPage.waitElementLocated(cartItemsAmountXpath, driver);
        Assert.assertEquals("1", driver.findElement(By.xpath(cartItemsAmountXpath)).getText());//check cart items = 1;

    }

    @Test(priority = 2)
    public void testDeleteItems() {

        By cartLabelXpath = By.xpath(".//*[@id='header']/div/div[2]/div/div/a/span[2]");
        driver.findElement(cartLabelXpath).click();; //view cart options

        By viewShoppingCartXpath = By.xpath(".//*[@id='header-cart']/div[3]/div[4]/a");
        driver.findElement(viewShoppingCartXpath).click();//view shopping cart

        String emptyCartButtonXpath= ".//*[@id='empty_cart_button']";
        WaitPage.waitElementLocated(emptyCartButtonXpath, driver);
        driver.findElement(By.xpath(emptyCartButtonXpath)).click();// click Empty cart

        String cartItemsAmountXpath = ".//*[@id='header']/div/div[2]/div/div/a/span[3]";
        WaitPage.waitElementLocated(cartItemsAmountXpath, driver);
        Assert.assertFalse(driver.findElement(By.xpath(cartItemsAmountXpath)).isDisplayed());
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
