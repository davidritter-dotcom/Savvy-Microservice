package ch.ffhs;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;


public class ChromeTest {

    @Test
    public void testSeleniumDocker() throws MalformedURLException {
        ChromeOptions caps = new ChromeOptions();
        URL hubUrl = new URL("http://localhost:4444/wd/hub");
        WebDriver driver = new RemoteWebDriver(hubUrl, caps);

        driver.get("https://www.google.com");
        System.out.println(driver.getTitle());


        driver.quit();
    }

}
