package Magento;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by kruzhitskaya on 03.03.15.
 */
public class WaitPage {
    public static void waitElement (String locator, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement waitElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));

    }
}
