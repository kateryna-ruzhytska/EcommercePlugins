package wp;

import shared.GenerateDataPage;
import shared.WaitPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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

}
