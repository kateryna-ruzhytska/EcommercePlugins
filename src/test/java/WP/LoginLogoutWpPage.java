package WP;

import Shared.WaitPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

/**
 * Created by kruzhitskaya on 31.03.15.
 */
public class LoginLogoutWpPage {

    public static void loginAction(String email, String pass, WebDriver driver) {

        String logInLabelXpath = ".//*[@id='meta-2']/ul/li[2]/a";
        driver.findElement(By.xpath(logInLabelXpath)).click();//click Log in label

        String loginFormXpath = ".//*[@id='loginform']";
        WaitPage.waitElementLocated(loginFormXpath, driver);//wait for login form

        setEmailPass(email, pass, driver);

        String logInButtonXpath = ".//*[@id='wp-submit']";
        driver.findElement(By.xpath(logInButtonXpath)).click();//click Log in button

        String baseUrl = "http://triggmine-02.videal.net/";
        driver.get(baseUrl);//go to the Home page
        String homePage = ".//*[@id='masthead']";
        WaitPage.waitElementLocated(homePage, driver);//check user is on the Home page

        String welcomeMessageXpath = ".//*[@id='wp-admin-bar-my-account']/a";
        Assert.assertTrue(driver.findElement(By.xpath(welcomeMessageXpath)).isDisplayed());//check user is logged in

    }

    public static void setEmailPass(String email, String pass, WebDriver driver) {

        String emailXpath = ".//*[@id='user_login']";
        driver.findElement(By.xpath(emailXpath)).clear();
        driver.findElement(By.xpath(emailXpath)).sendKeys(email);//set email

        String passXpath = ".//*[@id='user_pass']";
        driver.findElement(By.xpath(passXpath)).clear();
        driver.findElement(By.xpath(passXpath)).sendKeys(pass);//set password
    }

    public static void logOutAction(WebDriver driver) {

        WebElement welcome = driver.findElement(By.xpath(".//*[@id='wp-admin-bar-my-account']/a"));
        Actions builder = new Actions(driver);
        builder.moveToElement(welcome).build().perform();//set cursor position on welcome label

        String logOutLabel = ".//*[@id='wp-admin-bar-logout']/a";
        WaitPage.waitElementClickable(logOutLabel, driver);
        driver.findElement(By.xpath(logOutLabel)).click();// click Log Out

        String backToShopXpath = ".//*[@id='backtoblog']/a";
        driver.findElement(By.xpath(backToShopXpath)).click(); //go back to the Home page

    }
}
