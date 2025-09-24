//package com.sayuru.Bodima_backend.selenium;
//
//import org.junit.jupiter.api.*;
//import org.openqa.selenium.*;
//import org.openqa.selenium.chrome.ChromeDriver;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class AddPlaceUITest {
//
//    private WebDriver driver;
//
//    @BeforeEach
//    void setUp() {
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        driver.get("http://localhost:5173/login");
//
//        // Login first (reuse login steps)
//        driver.findElement(By.id("username")).sendKeys("testuser");
//        driver.findElement(By.id("password")).sendKeys("password123");
//        driver.findElement(By.id("loginBtn")).click();
//    }
//
//    @Test
//    void testAddPlace() {
//        // Navigate to Add Place page
//        driver.findElement(By.id("addPlaceLink")).click();
//
//        // Fill form
//        driver.findElement(By.id("place_name")).sendKeys("Test Place");
//        driver.findElement(By.id("address")).sendKeys("Colombo, Sri Lanka");
//        driver.findElement(By.id("rent")).sendKeys("5000");
//        driver.findElement(By.id("rooms")).sendKeys("2");
//        driver.findElement(By.id("submitBtn")).click();
//
//        // Verify place added
//        WebElement successMsg = driver.findElement(By.id("successMessage"));
//        assertEquals("Place added successfully!", successMsg.getText());
//    }
//
//    @AfterEach
//    void tearDown() {
//        driver.quit();
//    }
//}
