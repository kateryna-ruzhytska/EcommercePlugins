package WP;

import Shared.WaitPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by kruzhitskaya on 31.03.15.
 */
public class LoginLogoutWpPage {

    public static void LoginAction(String email, String pass, WebDriver driver) {

        String logInLabelXpath = ".//*[@id='meta-2']/ul/li[2]/a";
        driver.findElement(By.xpath(logInLabelXpath)).click();//click Log in label

        setEmailPass(email, pass, driver);

        String logInButtonXpath = ".//*[@id='wp-submit']";
        driver.findElement(By.xpath(logInButtonXpath)).click();//click Log in button

    }

    public static void setEmailPass(String email, String pass, WebDriver driver) {

        String loginFormXpath = ".//*[@id='loginform']";
        WaitPage.waitElementLocated(loginFormXpath, driver);//wait for login form

        String emailXpath = ".//*[@id='user_login']";
        driver.findElement(By.xpath(emailXpath)).clear();
        driver.findElement(By.xpath(emailXpath)).sendKeys(email);//set email

        String passXpath = ".//*[@id='user_pass']";
        driver.findElement(By.xpath(passXpath)).clear();
        driver.findElement(By.xpath(passXpath)).sendKeys(pass);//set password
    }

    public static void logOutAction() {


    }
}
