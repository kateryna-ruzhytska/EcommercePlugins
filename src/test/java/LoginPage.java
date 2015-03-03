import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by kruzhitskaya on 25.02.15.
 */
public class LoginPage {
    public static void loginAs(String email, String pass, WebDriver driver){
        typeEmail(email, driver);
        typePassword(pass, driver);
        driver.findElement(By.cssSelector("[type=\"submit\"]")).click();
    }
    private static void type(String locator, String value, WebDriver driver){
        driver.findElement(By.id(locator)).clear();
        driver.findElement(By.id(locator)).sendKeys(value);
    }

    private static void typeEmail(String email, WebDriver driver){
        type("UserName", email, driver);
    }
    private static void typePassword(String pass, WebDriver driver){
        type("Password", pass, driver);
    }
}
