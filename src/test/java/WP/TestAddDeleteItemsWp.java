package WP;

import Shared.BrowserMobProxyPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by kruzhitskaya on 31.03.15.
 */
public class TestAddDeleteItemsWp {
    private WebDriver driver;
    private String baseUrl = "http://triggmine-02.videal.net/";

   @BeforeTest
   public void proxyServer() throws Exception {
       BrowserMobProxyPage.startProxy();
       driver = BrowserMobProxyPage.desiredCap();
//       ProxyServer bmp = new ProxyServer(8071);
//       DesiredCapabilities caps = new DesiredCapabilities();
//       caps.setCapability(CapabilityType.PROXY, bmp.seleniumProxy());
//
//       driver = new FirefoxDriver(caps);
   }

    @BeforeTest(dependsOnMethods = "proxyServer")
    public void setUp() {
        BrowserMobProxyPage.newHar();
        driver.get(baseUrl);
        driver.manage().deleteAllCookies();

    }

    @Test(priority = 1)
    public void testAddItem() {

        AddDeleteItemsWpPage.addItem(driver);//add item

        AddDeleteItemsWpPage.addItem(driver);//update cart

        AddDeleteItemsWpPage.deleteItem(driver);//clear cart

        BrowserMobProxyPage.gettingHar();
    }



    @AfterTest
    public void tearDown() throws Exception {
        driver.quit();
        BrowserMobProxyPage.stopProxy();
    }
}
