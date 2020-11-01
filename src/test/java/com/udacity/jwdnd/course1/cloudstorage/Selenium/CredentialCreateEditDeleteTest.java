package com.udacity.jwdnd.course1.cloudstorage.Selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CredentialCreateEditDeleteTest {
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
        this.webDriverWait = new WebDriverWait(webDriver, 2);

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
    public void insertCredential() {
        this.webDriver.get("http://localhost:" + this.port + "/home");
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab")));
        WebElement credentialTab = this.webDriver.findElement(By.id("nav-credentials-tab"));
        credentialTab.click();

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credentialCreateButton")));
        WebElement credentialCreateButton = this.webDriver.findElement(By.id("credentialCreateButton"));
        credentialCreateButton.click();

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url")));
        WebElement urlField = this.webDriver.findElement(By.id("credential-url"));
        urlField.sendKeys("https://google.com");
        WebElement usernameField = this.webDriver.findElement(By.id("credential-username"));
        usernameField.sendKeys("SomeUsername");
        WebElement passwordField = this.webDriver.findElement(By.id("credential-password"));
        passwordField.sendKeys("SomePassword");

        WebElement credentialFooterSubmit = this.webDriver.findElement(By.id("credentialFooterSubmit"));
        credentialFooterSubmit.click();

        this.webDriverWait.until(ExpectedConditions.titleContains("Result"));
        Assertions.assertEquals("Result", webDriver.getTitle());

        Assertions.assertDoesNotThrow(() -> {
            this.webDriver.findElement(By.id("resultSaveSuccess"));
        });

        this.webDriver.get("http://localhost:" + this.port + "/home");
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credential-tab")));
        WebElement credentialTab2 = this.webDriver.findElement(By.id("nav-credential-tab"));
        credentialTab2.click();

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("noteCreateButton")));
        Assertions.assertDoesNotThrow(() -> {
            this.webDriver.findElement(By.xpath("//th[text()='https://google.com']"));
            this.webDriver.findElement(By.xpath("//td[text()='SomeUsername']"));
            this.webDriver.findElement(By.xpath("//td[text()='SomePassword']"));
        });
    }


    // TODO Extract into utils (duplicate code)
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
