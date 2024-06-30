package ch.ffhs;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;


public class ChromeTest {

    // Define important variables
    private static WebDriver driver;

    @BeforeAll
    public static void setUp() throws MalformedURLException {
        ChromeOptions caps = new ChromeOptions();
        caps.setExperimentalOption("useAutomationExtension", false);
        caps.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        URL hubUrl = new URL("http://localhost:4444/wd/hub");
        driver = new RemoteWebDriver(hubUrl, caps);
    }

    @Test
    public void testTodoLists() {
        login();

        final String todoListTitle = "Todo List for testing";

        new WebDriverWait(driver, ofSeconds(30), ofSeconds(1))
                .until(titleIs("Todo Lists"));

        WebElement titelInput = driver.findElement(By.id("input-vaadin-text-field-3"));
        titelInput.sendKeys(todoListTitle);
        WebElement addButton = driver.findElement(By.id("add-todo-list-button"));
        addButton.click();

        var xPathStart = "//vaadin-grid-cell-content[contains(.,'";
        var xPathEnd = "')]";

        //Waits for the new Todo List to be Added
        new WebDriverWait(driver, ofSeconds(30), ofSeconds(1))
                .until(visibilityOfElementLocated(By.xpath(xPathStart + todoListTitle + xPathEnd)));

        var todoListGridCell = driver.findElement(By.xpath(xPathStart + todoListTitle + xPathEnd));
        var editButton = todoListGridCell.findElement(By.xpath(xPathStart + todoListTitle + xPathEnd + "/following-sibling::vaadin-grid-cell-content//vaadin-button"));

        

        assertEquals(todoListTitle, todoListGridCell.getText());
    }

    private void login() {
        String baseUrl = "http://host.docker.internal:8080";
        driver.get(baseUrl);

        new WebDriverWait(driver, ofSeconds(30), ofSeconds(1))
                .until(titleIs("Login"));

        driver.findElement(By.id("auth0-login-link-lv")).click();

        new WebDriverWait(driver, ofSeconds(30), ofSeconds(1))
                .until(titleIs("Log in | savvy"));

        WebElement usernameField = driver.findElement(By.id("username"));
        String username = "david.ritter@gmx.ch";
        usernameField.sendKeys(username);

        WebElement passwordField = driver.findElement(By.id("password"));
        String password = "Test1234Password";
        passwordField.sendKeys(password);

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit'][data-action-button-primary='true']"));
        submitButton.click();
    }
}
