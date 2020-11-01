package com.udacity.jwdnd.course1.cloudstorage.Selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
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

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.webDriver = new ChromeDriver();
        this.webDriverWait = new WebDriverWait(webDriver, 1000);

        this.userSignup();
        this.userLogin();
    }

    @AfterEach
    public void afterEach() {
        if(this.webDriver != null) {
            this.webDriver.quit();
        }
    }

    @Test
    @Order(1)
    public void insertNote() {
        this.webDriver.get("http://localhost:" + this.port + "/home");
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab")));
        WebElement notesTab = this.webDriver.findElement(By.id("nav-notes-tab"));
        notesTab.click();

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("noteCreateButton")));
        WebElement noteCreateButton = this.webDriver.findElement(By.id("noteCreateButton"));
        noteCreateButton.click();

        WebElement titleField = this.webDriver.findElement(By.id("note-title"));
        titleField.sendKeys("Note Title");
        WebElement descriptionField = this.webDriver.findElement(By.id("note-description"));
        descriptionField.sendKeys("Note Description");

        WebElement noteSubmitButton = this.webDriver.findElement(By.id("noteSubmit"));
        noteSubmitButton.click();

        Assertions.assertDoesNotThrow(() -> {
            this.webDriver.findElement(By.xpath("//td[text()='Note Title']"));
            this.webDriver.findElement(By.xpath("//td[text()='Note Description']"));
        });
    }

    // TODO Issue with interacting with submit button??

    // Utility
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