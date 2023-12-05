import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoginPageTest {

    private WebDriver driver;
    private String baseUrl;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "./chromeDriver.exe");
        driver = new ChromeDriver();
        baseUrl = "http://localhost:3000"
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testLoginWithValidCredentials() {
        driver.get(baseUrl + "/login");
        
        driver.findElement(By.id("email")).sendKeys("valid@example.com");
        driver.findElement(By.id("password")).sendKeys("validpassword");
        
        driver.findElement(By.cssSelector(".btn.btn-primary")).click();
        
        assertEquals(baseUrl + "/", driver.getCurrentUrl());
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        driver.get(baseUrl + "/login");
        
        driver.findElement(By.id("email")).sendKeys("invalid@example.com");
        driver.findElement(By.id("password")).sendKeys("invalidpassword");
        
        driver.findElement(By.cssSelector(".btn.btn-primary")).click();
        
        WebElement errorMessage = driver.findElement(By.cssSelector(".alert-danger"));
        assertTrue(errorMessage.isDisplayed());
        assertEquals("INVALID CREDENTIALS", errorMessage.getText().trim());
    }

    @Test
    public void testRegistrationWithValidData() {
        driver.get(baseUrl + "/register");
        
        driver.findElement(By.id("username")).sendKeys("newuser");
        driver.findElement(By.id("email")).sendKeys("newuser@example.com");
        driver.findElement(By.id("password")).sendKeys("newuserpassword");
        // Click on the register button
        driver.findElement(By.cssSelector(".btn.btn-primary")).click();
        
        assertEquals(baseUrl + "/dashboard", driver.getCurrentUrl());
    }

    @Test
    public void testRegistrationWithExistingEmail() {
        driver.get(baseUrl + "/register");
        
        driver.findElement(By.id("username")).sendKeys("existinguser");
        driver.findElement(By.id("email")).sendKeys("existing@example.com");
        driver.findElement(By.id("password")).sendKeys("newuserpassword");
        
        driver.findElement(By.cssSelector(".btn.btn-primary")).click();
        
        WebElement errorMessage = driver.findElement(By.cssSelector(".alert-danger"));
        assertTrue(errorMessage.isDisplayed());
        assertEquals("Email already exists", errorMessage.getText().trim());
    }

    @Test
    public void testNavigationToRegistrationPage() {
        driver.get(baseUrl + "/login");
        
        driver.findElement(By.linkText("Register")).click();
        
        assertEquals(baseUrl + "/register", driver.getCurrentUrl());
    }
}
