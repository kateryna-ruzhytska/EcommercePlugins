package Magento;

import Shared.WaitPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * Created by kruzhitskaya on 30.03.15.
 */
public class LoginLogoutMgnPage {

    public static void logInAction(String email, String pass,WebDriver driver) {

        By accountButtonXpath = By.xpath(".//*[@id='header']/div/div[2]/div/a/span[2]");
        driver.findElement(accountButtonXpath).click();// click Account button

        String logInLabelXpath = ".//*[@id='header-account']/div/ul/li[6]/a";
        WaitPage.waitElementClickable(logInLabelXpath, driver);
        driver.findElement(By.xpath(logInLabelXpath)).click();//choose Log In option

        emailPass(email, pass, driver);//set email and password

        String loginButtonXpath = ".//*[@id='send2']";
        driver.findElement(By.xpath(loginButtonXpath)).click();//click Login button

        String welcomeXpath = ".//*[@id='top']/body/div[1]/div/div[1]/div/p";
        WaitPage.waitElementLocated(welcomeXpath, driver);
        Assert.assertTrue(driver.findElement(By.xpath(welcomeXpath)).isDisplayed());

    }

    public static void emailPass(String email, String pass,WebDriver driver) {
        String emailXpath = ".//*[@id='email']";
        WaitPage.waitElementLocated(emailXpath, driver);
        driver.findElement(By.xpath(emailXpath)).clear();
        driver.findElement(By.xpath(emailXpath)).sendKeys(email);//set email

        String passwordXpath = ".//*[@id='pass']";
        driver.findElement(By.xpath(passwordXpath)).clear();
        driver.findElement(By.xpath(passwordXpath)).sendKeys(pass);//set password

    }

    public static void logOutAction(WebDriver driver){

        By accountButtonXpath = By.xpath(".//*[@id='header']/div/div[2]/div/a/span[2]");
        driver.findElement(accountButtonXpath).click();// click Account button

        String logOutLabelXpath = ".//*[@id='header-account']/div/ul/li[5]/a";
        WaitPage.waitElementLocated(logOutLabelXpath, driver);
        driver.findElement(By.xpath(logOutLabelXpath)).click();//click Log Out label
    }
}
