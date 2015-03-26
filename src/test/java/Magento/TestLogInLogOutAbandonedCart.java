package Magento;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by kruzhitskaya on 27.03.15.
 */
public class TestLogInLogOutAbandonedCart {
    private WebDriver driver;
    private String baseUrl = "http://triggmine-05.videal.net/";

    @BeforeTest
    public void setUp() {
        driver = new FirefoxDriver();

    }

    @Test
    public void testLogInLogOutAbandonedCart (){
        driver.get(baseUrl);

        By accountButtonXpath = By.xpath(".//*[@id='header']/div/div[2]/div/a/span[2]");
        driver.findElement(accountButtonXpath).click();// click Account button

        String logInLabelXpath= ".//*[@id='header-account']/div/ul/li[6]/a";
        WaitPage.waitElementClickable(logInLabelXpath, driver);
        driver.findElement(By.xpath(logInLabelXpath)).click();//choose Log In option

        String emailXpath = ".//*[@id='email']";
        WaitPage.waitElementLocated(emailXpath, driver);
        driver.findElement(By.xpath(emailXpath)).clear();
        driver.findElement(By.xpath(emailXpath)).sendKeys("triggmine02@gmail.com");//set email

        String passwordXpath = ".//*[@id='pass']";
        driver.findElement(By.xpath(passwordXpath)).clear();
        driver.findElement(By.xpath(passwordXpath)).sendKeys("0508101626");//set password

        String loginButtonXpath = ".//*[@id='send2']";
        driver.findElement(By.xpath(loginButtonXpath)).click();//click Login button

        driver.get(baseUrl + "/index.php/plat-ja-ot-olega/plat-ja-s-dlinnym-rukavom.html");

        By addItemButtonXpath = By.xpath(".//*[@id='top']/body/div[1]/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[1]/div/div[2]/button");
        driver.findElement(addItemButtonXpath).click();//add item to the cart

        driver.findElement(accountButtonXpath).click();// click Account button

        String logOutLabelXpath = ".//*[@id='header-account']/div/ul/li[5]/a";
        WaitPage.waitElementLocated(logOutLabelXpath, driver);
        driver.findElement(By.xpath(logOutLabelXpath)).click();//click Log Out label

    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
