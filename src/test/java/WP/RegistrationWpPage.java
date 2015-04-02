package WP;

import Shared.GenerateDataPage;
import Shared.WaitPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

/**
 * Created by kruzhitskaya on 01.04.15.
 */
public class RegistrationWpPage {

    public static void registration(WebDriver driver) {

        String registerLinkXpath = ".//*[@id='meta-2']/ul/li[1]/a";
        driver.findElement(By.xpath(registerLinkXpath)).click();//click Register link

        String regFormXpath = ".//*[@id='registerform']";
        WaitPage.waitElementLocated(regFormXpath, driver);
        Assert.assertTrue(driver.findElement(By.xpath(regFormXpath)).isDisplayed());//registration form is present

        randomUsernameEmail(driver);//set username and email

        LoginLogoutWpPage.clickLoginButton(driver);//click Login button

    }

    public static void randomUsernameEmail (WebDriver driver) {
        GenerateDataPage genData = new GenerateDataPage();

        String usernameFieldXpath = ".//*[@id='user_login']";
        driver.findElement(By.xpath(usernameFieldXpath)).clear();
        driver.findElement(By.xpath(usernameFieldXpath)).sendKeys("Kate" +  genData.generateRandomNumber(4));

        String emailFieldXpath = ".//*[@id='user_email']";
        driver.findElement(By.xpath(emailFieldXpath)).clear();
        driver.findElement(By.xpath(emailFieldXpath)).
                sendKeys("triggminefortest" + "+" + genData.generateRandomNumber(4) + "@gmail.com");
    }

    public static void getGmailData(WebDriver driver2) {

        driver2 = new FirefoxDriver();
        String gmailUrl = "https://www.gmail.com";
        driver2.get(gmailUrl);

        String loginFormGmailXpath = "html/body/div[1]/div[2]/div[2]";
        WaitPage.waitElementLocated(loginFormGmailXpath, driver2);

        String emailGmailXpath = ".//*[@id='Email']";
        driver2.findElement(By.xpath(emailGmailXpath)).clear();
        driver2.findElement(By.xpath(emailGmailXpath)).sendKeys("triggminefortest@gmail.com");

        String passGmailXpath = ".//*[@id='Passwd']";
        driver2.findElement(By.xpath(passGmailXpath)).clear();
        driver2.findElement(By.xpath(passGmailXpath)).sendKeys("0508101626");

        String signInGmailXpath = ".//*[@id='signIn']";
        driver2.findElement(By.xpath(signInGmailXpath)).click();

        driver2.quit();

    }

}
