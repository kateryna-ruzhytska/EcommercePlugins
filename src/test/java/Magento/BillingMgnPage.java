package Magento;

import Shared.GenerateDataPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by kruzhitskaya on 02.03.15.
 */
public class BillingMgnPage {
    public static void fillBilling (String firstName, String lastName,
                                    String address, String city, String zipCode,
                                               String phone, WebDriver driver){
        typeFirstName(firstName, driver);
        typeLastName(lastName, driver);
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


    public static void chooseStateProvince(WebDriver driver){

        String stateProvinceXpath = ".//*[@id='billing:region_id']";
        driver.findElement(By.xpath(stateProvinceXpath)).click(); //select State/Province
        final Select select = new Select (driver.findElement(By.xpath(stateProvinceXpath)));
        final int optionIndex = 3;
        WebElement optionThird = select.getOptions().get(optionIndex);
        optionThird.click();

    }

    public static void enterPassConfirmPass(String pass, WebDriver driver) {

        By passwordXpath = By.xpath(".//*[@id='billing:customer_password']");
        driver.findElement(passwordXpath).clear();
        driver.findElement(passwordXpath).sendKeys(pass);

        By confirmPasswordXpath = By.xpath(".//*[@id='billing:confirm_password']");
        driver.findElement(confirmPasswordXpath).clear();
        driver.findElement(confirmPasswordXpath).sendKeys(pass);

    }

    public static void generateNewEmail( WebDriver driver) {
        GenerateDataPage genData = new GenerateDataPage();

        driver.findElement(By.xpath(".//*[@id='billing:email']")).clear();
        driver.findElement(By.xpath(".//*[@id='billing:email']")).sendKeys(genData.generateEmail(30));//generate random email
    }

    public static void enterEmail(String email, WebDriver driver) {
        driver.findElement(By.xpath(".//*[@id='billing:email']")).clear();
        driver.findElement(By.xpath(".//*[@id='billing:email']")).sendKeys(email);//type email
    }


}
