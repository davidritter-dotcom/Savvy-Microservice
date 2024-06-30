package ch.ffhs;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

import static java.time.Duration.ofSeconds;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class ChromeTest {

    @Test
    public void testSeleniumDocker() throws MalformedURLException {
        ChromeOptions caps = new ChromeOptions();
        URL hubUrl = new URL("http://localhost:4444/wd/hub");
        WebDriver driver = new RemoteWebDriver(hubUrl, caps);

        try {
            driver.get("http://host.docker.internal:8080");

            //Have to explicitly wait because it takes time for compiled html to load
            new WebDriverWait(driver, ofSeconds(30), ofSeconds(1))
                    .until(titleIs("Login"));

            System.out.println(driver.getTitle());
            //Suche nach dem Element mit der ID
            WebElement element = driver.findElement(By.id("logo-lv"));
            System.out.println("Element Text: " + element.getText());

        } finally {
            driver.quit();
        }

    }

}
