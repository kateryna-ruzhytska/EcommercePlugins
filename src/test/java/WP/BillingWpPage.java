package WP;

import Shared.GenerateDataPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by kruzhitskaya on 02.04.15.
 */
public class BillingWpPage {

    public static void setBillingInf(String firstName,String lastName,String address,
                                     String city,String stateProvince,
                                     String shippingStateProvince, WebDriver driver) {

        setFirstName(firstName, driver);
        setLastName(lastName, driver);
        setAddress(address, driver);
        setCity(city, driver);
        setStateProvince(stateProvince, driver);
        setShippingStateProvince(shippingStateProvince, driver);
    }
    public static void setEmail(String email,WebDriver driver){
        String emailXpath = ".//*[@id='wpsc_checkout_form_9']";
        driver.findElement(By.xpath(emailXpath)).clear();
        driver.findElement(By.xpath(emailXpath)).sendKeys(email);
    }
    public static void setRandomEmail(WebDriver driver) {
        GenerateDataPage genData = new GenerateDataPage();
        String emailXpath = ".//*[@id='wpsc_checkout_form_9']";
        driver.findElement(By.xpath(emailXpath)).clear();
        driver.findElement(By.xpath(emailXpath)).sendKeys(genData.generateEmail(30));
    }
    public static void selectCountry(WebDriver driver) {
        String selectCountryXpath = ".//*[@id='wpsc_checkout_form_7']";
        driver.findElement(By.xpath(selectCountryXpath)).click();
        final Select select = new Select(driver.findElement(By.xpath(selectCountryXpath)));
        final int optionIndex = 3;
        WebElement optionThird = select.getOptions().get(optionIndex);
        optionThird.click();
    }


    public static void setFirstName(String firstName,WebDriver driver){
        String firstNameXpath = ".//*[@id='wpsc_checkout_form_2']";
        type(firstNameXpath, firstName, driver);
    }
    public static void setLastName(String lastName,WebDriver driver){
        String lastNameXpath = ".//*[@id='wpsc_checkout_form_3']";
        type(lastNameXpath, lastName, driver);
    }
    public static void setAddress(String address,WebDriver driver){
        String addressXpath = ".//*[@id='wpsc_checkout_form_4']";
        type(addressXpath, address, driver);
    }
    public static void setCity(String city,WebDriver driver){
        String cityXpath = ".//*[@id='wpsc_checkout_form_5']";
        type(cityXpath, city, driver);
    }
    public static void setStateProvince(String stateProvince,WebDriver driver){
        String stateProvinceXpath = ".//*[@id='wpsc_checkout_form_6']";
        type(stateProvinceXpath, stateProvince, driver);
    }
    public static void setShippingStateProvince(String shippingStateProvince,WebDriver driver){
        String shippingStateProvinceXpath = ".//*[@id='wpsc_checkout_form_15']";
        type(shippingStateProvinceXpath, shippingStateProvince, driver);
    }
    private static void type (String locator,String value, WebDriver driver) {
        driver.findElement(By.xpath(locator)).clear();
        driver.findElement(By.xpath(locator)).sendKeys(value);
    }

}
