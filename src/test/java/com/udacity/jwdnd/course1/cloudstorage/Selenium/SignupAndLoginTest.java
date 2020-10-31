package com.udacity.jwdnd.course1.cloudstorage.Selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignupAndLoginTest {
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
    }

    @AfterEach
    public void afterEach() {
        if(this.webDriver != null) {
            this.webDriver.quit();
        }
    }

    @Test
    // Test home page loads without error messages
    public void homeWithoutMessage() {
        this.webDriver.get("http://localhost:" + this.port + "/home");
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            this.webDriver.findElement(By.id("invalidUserPassMsg"));
        });
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            this.webDriver.findElement(By.id("userLoggedOutMsg"));
        });
    }

    @Test
    // Test redirect to login without being logged in
    public void homeUnauthorized() {
        this.webDriver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Login", this.webDriver.getTitle());
    }

    @Test
    public void invalidLogin() {
        this.webDriver.get("http://localhost:" + this.port + "/login");

        WebElement usernameField = webDriver.findElement(By.id("inputUsername"));
        usernameField.sendKeys("someAdmin");
        WebElement passwordField = webDriver.findElement(By.id("inputPassword"));
        passwordField.sendKeys("somePassword");
        WebElement submitButton = webDriver.findElement(By.id("loginSubmitBtn"));
        submitButton.click();

        Assertions.assertDoesNotThrow(() -> {
            this.webDriver.findElement(By.id("invalidUserPassMsg"));
        });
    }

    @Test
    public void userSignupAndLogin() {
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

        Assertions.assertDoesNotThrow(() -> {
            this.webDriver.findElement(By.id("signupSuccessMsg"));
        });

        WebElement toLoginLink = this.webDriver.findElement(By.id("toLoginHref"));
        toLoginLink.click();

        this.webDriverWait.until(ExpectedConditions.titleContains("Login"));
        Assertions.assertEquals("Login", webDriver.getTitle());

        WebElement usernameField = webDriver.findElement(By.id("inputUsername"));
        usernameField.sendKeys("admin");
        WebElement passwordField = webDriver.findElement(By.id("inputPassword"));
        passwordField.sendKeys("password");
        WebElement loginButton = webDriver.findElement(By.id("loginSubmitBtn"));
        loginButton.click();

        this.webDriverWait.until(ExpectedConditions.titleContains("Home"));
        Assertions.assertEquals("Home", webDriver.getTitle());
    }

/*    @Test
    public userLogout() {
        this.userSignupAndLogin();

        WebElement logoutButton = webDriver.findElement(By.id("logoutButton"));
        logoutButton.click();

    }*/

}
