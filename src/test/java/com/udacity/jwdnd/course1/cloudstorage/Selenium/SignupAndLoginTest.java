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
public class SignupAndLoginTest {
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
    public void homeUnauthorized() {
        this.webDriver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Login", this.webDriver.getTitle());
    }

    @Test
    public void invalidLogin() {
        this.webDriver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", webDriver.getTitle());

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("inputUsername"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("inputUsername")));
        this.jsWebDriver.executeScript("arguments[0].value='" + "someAdmin" + "';", webDriver.findElement(By.id("inputUsername")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("inputPassword"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("inputPassword")));
        this.jsWebDriver.executeScript("arguments[0].value='" + "somePassword" + "';", webDriver.findElement(By.id("inputPassword")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("loginSubmitBtn"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("loginSubmitBtn")));
        Assertions.assertDoesNotThrow(() -> {
            this.webDriver.findElement(By.id("invalidUserPassMsg"));
        });
    }

    @Test
    public void userSignupAndLogin() {
        this.userSignupAndLogin("admin", "password");
    }

    @Test
    public void userLogout() {
        this.userSignupAndLogin("testLogoutAdmin", "testLogoutPassword");

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("logoutButton")))); //wait the element
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", webDriver.findElement(By.id("logoutButton"))); //click the element

        this.webDriverWait.until(ExpectedConditions.titleContains("Login"));
        Assertions.assertEquals("Login", webDriver.getTitle());
        Assertions.assertDoesNotThrow(() ->
            this.webDriver.findElement(By.id("userLoggedOutMsg")));

        this.webDriver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Login", this.webDriver.getTitle());
    }

    private void userSignupAndLogin(String username, String password) {
        this.webDriver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", webDriver.getTitle());

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("inputFirstName"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("inputFirstName")));
        this.jsWebDriver.executeScript("arguments[0].value='" + "AdminFN" + "';", webDriver.findElement(By.id("inputFirstName")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("inputLastName"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("inputLastName")));
        this.jsWebDriver.executeScript("arguments[0].value='" + "AdminLN" + "';", webDriver.findElement(By.id("inputLastName")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("inputUsername"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("inputUsername")));
        this.jsWebDriver.executeScript("arguments[0].value='" + username + "';", webDriver.findElement(By.id("inputUsername")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("inputPassword"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("inputPassword")));
        this.jsWebDriver.executeScript("arguments[0].value='" + password + "';", webDriver.findElement(By.id("inputPassword")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("signupSubmitButton"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("signupSubmitButton")));

        Assertions.assertDoesNotThrow(() -> {
            this.webDriver.findElement(By.id("signupSuccessMsg"));
        });

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("toLoginHref"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("toLoginHref")));

        this.webDriverWait.until(ExpectedConditions.titleContains("Login"));
        Assertions.assertEquals("Login", webDriver.getTitle());

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("inputUsername"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("inputUsername")));
        this.jsWebDriver.executeScript("arguments[0].value='" + username + "';", webDriver.findElement(By.id("inputUsername")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("inputPassword"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("inputPassword")));
        this.jsWebDriver.executeScript("arguments[0].value='" + password + "';", webDriver.findElement(By.id("inputPassword")));

        this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.id("loginSubmitBtn"))));
        this.jsWebDriver.executeScript("arguments[0].click();", webDriver.findElement(By.id("loginSubmitBtn")));

        this.webDriverWait.until(ExpectedConditions.titleContains("Home"));
        Assertions.assertEquals("Home", webDriver.getTitle());
    }
}
