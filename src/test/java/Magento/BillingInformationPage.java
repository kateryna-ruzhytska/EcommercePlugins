package Magento;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by kruzhitskaya on 02.03.15.
 */
public class BillingInformationPage {
    public static void fillBilling (String firstName, String lastName, /*String email,*/
                                    String address, String city, String zipCode,
                                               String phone, WebDriver driver){
        typeFirstName(firstName, driver);
        typeLastName(lastName, driver);
        //typeEmail(email, driver);
        typeAddress(address, driver);
        typeCity(city, driver);
        typeZipCode(zipCode, driver);
        typePhone(phone, driver);
    }
    private static void typeFirstName (String firstName, WebDriver driver) {
        type (".//*[@id='billing:firstname']", firstName, driver);
    }
    private static void typeLastName (String lastName, WebDriver driver) {
        type (".//*[@id='billing:lastname']", lastName, driver);
    }
    private static void typeEmail (String email, WebDriver driver) {
        type (".//*[@id='billing:email']", email, driver);
    }
    private static void typeAddress (String address, WebDriver driver) {
        type(".//*[@id='billing:street1']", address, driver);
    }
    private static void typeCity (String city, WebDriver driver) {
        type (".//*[@id='billing:city']", city, driver);
    }
    private static void typeZipCode (String zipCode, WebDriver driver) {
        type(".//*[@id='billing:postcode']", zipCode, driver);
    }
    private static void typePhone (String phone, WebDriver driver) {
        type(".//*[@id='billing:telephone']", phone, driver);
    }


    private static void type (String locator,String value, WebDriver driver) {
        driver.findElement(By.xpath(locator)).clear();
        driver.findElement(By.xpath(locator)).sendKeys(value);
    }


}
