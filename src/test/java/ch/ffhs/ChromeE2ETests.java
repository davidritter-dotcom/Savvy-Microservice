package ch.ffhs;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
import static org.junit.jupiter.api.Assertions.fail;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ChromeE2ETests {
    private static WebDriver driver;

    @BeforeAll
    public static void setUp() throws MalformedURLException {
        ChromeOptions caps = new ChromeOptions();
        caps.setExperimentalOption("useAutomationExtension", false);
        caps.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        URL hubUrl = new URL("http://localhost:4444/wd/hub");
        driver = new RemoteWebDriver(hubUrl, caps);
        login();
        new WebDriverWait(driver, ofSeconds(30), ofSeconds(1))
                .until(titleIs("Todo Lists"));
    }

    @Test
    @Order(1)
    public void testAddTodoLists() {
        final String todoListTitle = "Todo List for testing";

        new WebDriverWait(driver, ofSeconds(30), ofSeconds(1))
                .until(titleIs("Todo Lists"));

        WebElement titelInput = driver.findElement(By.id("add-todo-list-input"));
        titelInput.sendKeys(todoListTitle);
        WebElement addButton = driver.findElement(By.id("add-todo-list-button"));
        addButton.click();

        var xPathStart = "//vaadin-grid-cell-content[contains(.,'";
        var xPathEnd = "')]";

        //Waits for the new Todo List to be Added
        new WebDriverWait(driver, ofSeconds(30), ofSeconds(1))
                .until(visibilityOfElementLocated(By.xpath(xPathStart + todoListTitle + xPathEnd)));

        var todoListGridCell = driver.findElement(By.xpath(xPathStart + todoListTitle + xPathEnd));

        assertEquals(todoListTitle, todoListGridCell.getText());
    }

    @Test
    @Order(2)
    public void testEditTodoLists() {
        final String todoListTitle = "Todo List for testing";

        new WebDriverWait(driver, ofSeconds(30), ofSeconds(1))
                .until(titleIs("Todo Lists"));

        var xPathStart = "//vaadin-grid-cell-content[contains(.,'";
        var xPathEnd = "')]";

        var todoListGridCell = driver.findElement(By.xpath(xPathStart + todoListTitle + xPathEnd));

        var editButton = todoListGridCell.findElement(By.xpath(xPathStart + todoListTitle + xPathEnd + "/following-sibling::vaadin-grid-cell-content//vaadin-button"));
        editButton.click();

        new WebDriverWait(driver, ofSeconds(30), ofSeconds(1))
                .until(visibilityOfElementLocated(By.id("edit-input-field")));
        var editInput = driver.findElement(By.id("edit-input-field"));
        final String editedTitle = "Todo List for testing edited";
        editInput.sendKeys(" edited");
        var saveButton = driver.findElement(By.id("edit-save-button"));
        saveButton.click();

        new WebDriverWait(driver, ofSeconds(30), ofSeconds(1))
                .until(visibilityOfElementLocated(By.xpath(xPathStart + editedTitle + xPathEnd)));
        var editedTodoListGridCell = driver.findElement(By.xpath(xPathStart + editedTitle + xPathEnd));

        assertEquals(editedTitle, editedTodoListGridCell.getText());
    }

    @Test
    @Order(3)
    public void testDeleteTodoLists() {
        final String todoListTitle = "Todo List for testing edited";

        new WebDriverWait(driver, ofSeconds(30), ofSeconds(1))
                .until(titleIs("Todo Lists"));

        var xPathStart = "//vaadin-grid-cell-content[contains(.,'";
        var xPathEnd = "')]";

        var deleteButton = driver.findElement(By.xpath(xPathStart + todoListTitle + xPathEnd + "/following-sibling::*[2]//vaadin-button"));
        deleteButton.click();

        // Assert that the todoListGridCell does not exist anymore
        try {
            driver.navigate().refresh();
            new WebDriverWait(driver, ofSeconds(30), ofSeconds(1))
                    .until(titleIs("Todo Lists"));
            driver.findElement(By.xpath(xPathStart + todoListTitle + xPathEnd));

            // If we found the element, fail the test because it shouldn't exist
            fail("Todo list grid cell still exists after deletion");
        } catch (NoSuchElementException e) {
            System.out.println("Todo list successfully deleted");
        }
    }

    @AfterAll
    public static void clear(){
        driver.findElement(By.id("logout-button")).click();
        new WebDriverWait(driver, ofSeconds(30), ofSeconds(1))
                .until(titleIs("Login"));
        assertEquals(driver.getTitle(), "Login");
        driver.quit();
    }

    private static void login() {
        String baseUrl = "http://host.docker.internal:8087";
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
