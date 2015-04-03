package WP;

import com.triggmine.help.Parser;
import com.triggmine.mail.inspector.EmailReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.security.GeneralSecurityException;
import java.util.ArrayList;

/**
 * Created by kruzhitskaya on 01.04.15.
 */
public class TestRegistrationPurchaseWp {
    private WebDriver driver;
    private String baseUrl = "http://triggmine-02.videal.net/";
    private EmailReader emailReader = new EmailReader();
    private String login = "triggminefortest@gmail.com";
    private String password = "0508101626";
    private String regLogin;
    private String regPassword;


    @BeforeTest
    public void deleteMessages() throws GeneralSecurityException {
        emailReader.deleteAllMessages(login, password);
    }

    @BeforeTest(dependsOnMethods = "deleteMessages")
    public void setUp() {
        driver = new FirefoxDriver();
        driver.get(baseUrl);
        driver.manage().deleteAllCookies();
    }

    @Test(priority = 1)
    public void checkAddItem() {
        AddDeleteItemsWpPage.addItem(driver);//add item
    }


    @Test(priority = 2)
    public void performRegistration() {
        RegistrationWpPage.registration(driver);//register
    }

    @Test(priority = 3, timeOut = 120000)
    public void checkMailContent() {

        ArrayList<String> messageHtml = emailReader.readGmail(login, password);//check mail content
        regLogin = Parser.getCredentials(messageHtml.get(0), "Username: ");//get username
        regPassword = Parser.getCredentials(messageHtml.get(0), "Password: ");//get password
    }

    @Test(priority = 4)
    public void checkLogin(){
        LoginLogoutWpPage.loginActionAfterRegistration(regLogin, regPassword, driver);//log in with new credentials
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
