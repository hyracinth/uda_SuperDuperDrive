package com.udacity.jwdnd.course1.cloudstorage.Selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NoteCreateEditDeleteTest {
    @LocalServerPort
    private int port;

    private WebDriver webDriver;
    private WebDriverWait webDriverWait;
    private JavascriptExecutor jsWebDriver;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.webDriver = new ChromeDriver();
        this.webDriverWait = new WebDriverWait(webDriver, 2);
        this.jsWebDriver = (JavascriptExecutor) this.webDriver;

        this.userSignup();
        this.userLogin();
    }

    @AfterEach
    public void afterEach() {
        if (this.webDriver != null) {
            this.webDriver.quit();
        }
    }

    @Test
    @Order(1)
    public void insertNote() {
        this.webDriver.get("http://localhost:" + this.port + "/home");
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("nav-notes-tab"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("nav-notes-tab")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("noteCreateButton"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("noteCreateButton")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("note-title"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("note-title")));
        this.jsWebDriver.executeScript("arguments[0].value='" + "Note Title" + "';", webDriver.findElement(By.id("note-title")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("note-description"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("note-description")));
        this.jsWebDriver.executeScript("arguments[0].value='" + "Note Description" + "';", webDriver.findElement(By.id("note-description")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("noteFooterSubmit"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("noteFooterSubmit")));

        this.webDriverWait.until(ExpectedConditions.titleContains("Result"));
        Assertions.assertEquals("Result", webDriver.getTitle());

        Assertions.assertDoesNotThrow(() -> {
            this.webDriver.findElement(By.id("resultSaveSuccess"));
        });

        this.webDriver.get("http://localhost:" + this.port + "/home");
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("nav-notes-tab"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("nav-notes-tab")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("noteCreateButton")));
        Assertions.assertDoesNotThrow(() -> {
            this.webDriver.findElement(By.xpath("//th[text()='Note Title']"));
            this.webDriver.findElement(By.xpath("//td[text()='Note Description']"));
        });
    }

    @Test
    @Order(2)
    public void editNote() {
        this.webDriver.get("http://localhost:" + this.port + "/home");
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("nav-notes-tab"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("nav-notes-tab")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("editNoteButton"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("editNoteButton")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("note-title"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("note-title")));
        this.jsWebDriver.executeScript("arguments[0].value='" + "Note Title Updated" + "';", webDriver.findElement(By.id("note-title")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("note-description"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("note-description")));
        this.jsWebDriver.executeScript("arguments[0].value='" + "Note Description Updated" + "';", webDriver.findElement(By.id("note-description")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("noteFooterSubmit"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("noteFooterSubmit")));

        this.webDriverWait.until(ExpectedConditions.titleContains("Result"));
        Assertions.assertEquals("Result", webDriver.getTitle());

        Assertions.assertDoesNotThrow(() -> {
            this.webDriver.findElement(By.id("resultSaveSuccess"));
        });

        this.webDriver.get("http://localhost:" + this.port + "/home");
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("nav-notes-tab"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("nav-notes-tab")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("noteCreateButton")));
        Assertions.assertDoesNotThrow(() -> {
            this.webDriver.findElement(By.xpath("//th[text()='Note Title Updated']"));
            this.webDriver.findElement(By.xpath("//td[text()='Note Description Updated']"));
        });
    }

    @Test
    @Order(3)
    public void deleteNote() {
        this.webDriver.get("http://localhost:" + this.port + "/home");
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("nav-notes-tab"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("nav-notes-tab")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("deleteNoteButton"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("deleteNoteButton")));

        this.webDriverWait.until(ExpectedConditions.titleContains("Result"));
        Assertions.assertEquals("Result", webDriver.getTitle());

        Assertions.assertDoesNotThrow(() -> {
            this.webDriver.findElement(By.id("resultSaveSuccess"));
        });

        this.webDriver.get("http://localhost:" + this.port + "/home");
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("nav-notes-tab"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("nav-notes-tab")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("noteCreateButton")));
        Assertions.assertDoesNotThrow(() -> {
            this.webDriver.findElement(By.id("emptyNotesMsg"));
        });
    }

    // Utility functions
    // Sign up user
    private void userSignup() {
        this.webDriver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", webDriver.getTitle());

        WebElement fnField = this.webDriver.findElement(By.id("inputFirstName"));
        fnField.sendKeys("AdminFN");
        WebElement lnField = this.webDriver.findElement(By.id("inputLastName"));
        lnField.sendKeys("AdminLN");
        WebElement unField = this.webDriver.findElement(By.id("inputUsername"));
        unField.sendKeys("admin");
        WebElement pwField = this.webDriver.findElement(By.id("inputPassword"));
        pwField.sendKeys("password");
        WebElement submitButton = this.webDriver.findElement(By.id("signupSubmitButton"));
        submitButton.click();
    }

    // Logins user
    private void userLogin() {
        this.webDriver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", webDriver.getTitle());

        WebElement usernameField = webDriver.findElement(By.id("inputUsername"));
        usernameField.sendKeys("admin");
        WebElement passwordField = webDriver.findElement(By.id("inputPassword"));
        passwordField.sendKeys("password");
        WebElement loginButton = webDriver.findElement(By.id("loginSubmitBtn"));
        loginButton.click();
    }
}
