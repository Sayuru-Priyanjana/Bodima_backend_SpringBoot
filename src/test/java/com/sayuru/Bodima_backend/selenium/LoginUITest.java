package com.sayuru.Bodima_backend.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

public class LoginUITest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();  // requires chromedriver in PATH
        driver.manage().window().maximize();
        driver.get("http://localhost:5173/login"); // Your frontend login page
    }

    @Test
    void testUserLoginSuccess() {
        // Fill username + password
        driver.findElement(By.id("username")).sendKeys("testuser");
        driver.findElement(By.id("password")).sendKeys("password123");
        driver.findElement(By.id("loginBtn")).click();

        // Assertion: check if redirected to dashboard/home
        WebElement welcome = driver.findElement(By.id("welcomeMessage"));
        assertTrue(welcome.getText().contains("Welcome testuser"));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
