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
    public static void waitElementLocated(String locator, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        WebElement located = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
    }

    public static void waitElementClickable (String locator, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        WebElement located = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
        WebElement visible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
        WebElement clickable = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
    }
}
