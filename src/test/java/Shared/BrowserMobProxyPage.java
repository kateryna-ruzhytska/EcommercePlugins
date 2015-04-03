package Shared;

import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarRequest;
import net.lightbody.bmp.core.har.HarResponse;
import net.lightbody.bmp.proxy.ProxyServer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by kruzhitskaya on 03.04.15.
 */
public class BrowserMobProxyPage {
    private static ProxyServer bmp = new ProxyServer(8071);

    public static void startProxy() throws Exception{
        bmp.start();
    }

    public  static WebDriver desiredCap(){
        WebDriver driver;
        ProxyServer bmp = new ProxyServer(8071);
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(CapabilityType.PROXY, bmp.seleniumProxy());

        driver = new FirefoxDriver(caps);
        return driver;

    }

    public static void stopProxy() throws Exception{
        bmp.stop();
    }

    public static void newHar(){
        bmp.newHar("triggmine");
    }

    public static void gettingHar(){
        Har har = bmp.getHar();

        for (HarEntry entry : har.getLog().getEntries()) {
            HarRequest request = entry.getRequest();
            HarResponse response = entry.getResponse();

            System.out.println(request.getUrl() + " : " + response.getStatus()
                    + ", " + entry.getTime() + "ms");

            System.out.println(request.getPostData());

        }

    }

}
