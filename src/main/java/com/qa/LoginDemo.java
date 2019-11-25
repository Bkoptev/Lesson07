package com.qa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class LoginDemo {

    // Instance of WebDriver
    private WebDriver driver;
    private WebDriverWait wait;

    /**
     * Set up method
     */
    @Before
    public void setUp() {

        // If you want to disable infobars please use this code
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
        // Initialize path to ChromeDriver
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        // Initialize instance of ChromeDriver and add options
        driver = new ChromeDriver(options);
        // Set 10 second for implicitly wait
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        // Maximize window
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, 10);
        wait.withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofSeconds(2));
    }

    /**
     * Open test page, search and quit
     */
    @Test
    public void testPriceComparision() {

        // Test for  https://s1.demo.opensourcecms.com/s/44

        driver.get("https://s1.demo.opensourcecms.com/s/44");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='at4-share']")));
        //driver.switchTo().defaultContent();
        driver.switchTo().frame("preview-frame");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='txtUsername']")));
        driver.findElement(By.xpath("//input[@name='txtUsername']")).sendKeys("Login");
        driver.findElement(By.xpath("//input[@id='txtPassword']")).sendKeys("Password");
        driver.findElement(By.xpath("//input[@id='btnLogin']")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[@id='spanMessage' and text()='Invalid credentials']"))));
        driver.findElement(By.xpath("//input[@id='btnLogin']")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[@id='spanMessage' and text()='Username cannot be empty']"))));
        driver.findElement(By.xpath("//input[@id='txtUsername']")).sendKeys("Login");
        driver.findElement(By.xpath("//input[@id='btnLogin']")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[@id='spanMessage' and text()='Password cannot be empty']"))));
        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("//div[@class='preview__actions']//span")).click();

         if (driver.findElements(By.xpath("//div[@data-view='ctaHeader']")).size() < 1) {
             System.out.println("Test is success!");
         } else {
             System.out.println("Test failed!");
         }
    }

    /**
     * After method, quit driver
     */
    @After
    public void tearDown() {
        driver.quit();
    }
}