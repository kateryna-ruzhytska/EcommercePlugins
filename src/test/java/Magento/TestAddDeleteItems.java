package Magento;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
        driver.get(baseUrl + "/index.php/plat-ja-ot-olega/plat-ja-s-dlinnym-rukavom.html");
    }

    @Test(priority = 1)
    public void testAddItems() {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        driver.findElement(By.xpath(".//*[@id='top']/body/div[1]/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[1]/div/div[2]/button"))
                .click();  //add item to the cart
        WebElement waitElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".count")));
        Assert.assertEquals("1", driver.findElement(By.cssSelector(".count")).getText());

    }

    @Test(priority = 2)
    public void testDeleteItems() {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        driver.get(baseUrl + "/index.php/plat-ja-ot-olega/plat-ja-s-dlinnym-rukavom.html");
        driver.findElement(By.xpath(".//*[@id='top']/body/div[1]/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[1]/div/div[2]/button"))
                .click();  //add item to the cart
        driver.findElement(By.xpath(".//*[@id='header']/div/div[2]/div/div/a/span[2]")).click();//view cart options
        driver.findElement(By.xpath(".//*[@id='header-cart']/div[3]/div[4]/a")).click();//view shopping cart
        WebElement waitElement1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='empty_cart_button']")));
        driver.findElement(By.xpath(".//*[@id='empty_cart_button']")).click();

        WebElement waitElement2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".count")));
        Assert.assertFalse(driver.findElement(By.cssSelector(".count")).isDisplayed());
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
