package com.udacity.jwdnd.course1.cloudstorage.Selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
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
    public void insertCredential() {
        this.webDriver.get("http://localhost:" + this.port + "/home");
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("nav-credentials-tab"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("nav-credentials-tab")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("credentialCreateButton"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("credentialCreateButton")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("credential-url"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("credential-url")));
        this.jsWebDriver.executeScript("arguments[0].value='" + "https://google.com" + "';", webDriver.findElement(By.id("credential-url")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("credential-username"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("credential-username")));
        this.jsWebDriver.executeScript("arguments[0].value='" + "SomeUsername" + "';", webDriver.findElement(By.id("credential-username")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("credential-password"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("credential-password")));
        this.jsWebDriver.executeScript("arguments[0].value='" + "SomePassword" + "';", webDriver.findElement(By.id("credential-password")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("credentialFooterSubmit"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("credentialFooterSubmit")));

        this.webDriverWait.until(ExpectedConditions.titleContains("Result"));
        Assertions.assertEquals("Result", webDriver.getTitle());

        Assertions.assertDoesNotThrow(() -> {
            this.webDriver.findElement(By.id("resultSaveSuccess"));
        });

        this.webDriver.get("http://localhost:" + this.port + "/home");
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("nav-credentials-tab"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("nav-credentials-tab")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credentialCreateButton")));
        Assertions.assertDoesNotThrow(() -> {
            this.webDriver.findElement(By.xpath("//th[text()='https://google.com']"));
            this.webDriver.findElement(By.xpath("//td[text()='SomeUsername']"));
        });

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            this.webDriver.findElement(By.xpath("//td[text()='SomePassword']"));
        });
    }

    @Test
    @Order(2)
    public void editCredential() {
        this.webDriver.get("http://localhost:" + this.port + "/home");
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("nav-credentials-tab"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("nav-credentials-tab")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("editCredentialButton"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("editCredentialButton")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("credential-url"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("credential-url")));
        this.jsWebDriver.executeScript("arguments[0].value='" + "https://gmail.com" + "';", webDriver.findElement(By.id("credential-url")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("credential-username"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("credential-username")));
        this.jsWebDriver.executeScript("arguments[0].value='" + "SomeUsernameUpdated" + "';", webDriver.findElement(By.id("credential-username")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("credential-password"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("credential-password")));
        this.jsWebDriver.executeScript("arguments[0].value='" + "SomePasswordUpdated" + "';", webDriver.findElement(By.id("credential-password")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("credentialFooterSubmit"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("credentialFooterSubmit")));

        this.webDriverWait.until(ExpectedConditions.titleContains("Result"));
        Assertions.assertEquals("Result", webDriver.getTitle());

        Assertions.assertDoesNotThrow(() -> {
            this.webDriver.findElement(By.id("resultSaveSuccess"));
        });

        this.webDriver.get("http://localhost:" + this.port + "/home");
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("nav-credentials-tab"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("nav-credentials-tab")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credentialCreateButton")));
        Assertions.assertDoesNotThrow(() -> {
            this.webDriver.findElement(By.xpath("//th[text()='https://gmail.com']"));
            this.webDriver.findElement(By.xpath("//td[text()='SomeUsernameUpdated']"));
        });

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            this.webDriver.findElement(By.xpath("//td[text()='SomePasswordUpdated']"));
        });
    }

    @Test
    @Order(3)
    public void deleteNote() {
        this.webDriver.get("http://localhost:" + this.port + "/home");
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("nav-credentials-tab"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("nav-credentials-tab")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("deleteCredentialButton"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("deleteCredentialButton")));

        this.webDriverWait.until(ExpectedConditions.titleContains("Result"));
        Assertions.assertEquals("Result", webDriver.getTitle());

        Assertions.assertDoesNotThrow(() -> {
            this.webDriver.findElement(By.id("resultSaveSuccess"));
        });

        this.webDriver.get("http://localhost:" + this.port + "/home");
        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("nav-credentials-tab"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("nav-credentials-tab")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credentialCreateButton")));
        Assertions.assertDoesNotThrow(() -> {
            this.webDriver.findElement(By.id("emptyCredentialsMsg"));
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
