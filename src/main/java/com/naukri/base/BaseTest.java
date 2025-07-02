package com.naukri.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.time.Duration;
import java.util.UUID;

public class BaseTest {
    protected static WebDriver driver;
    protected static WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();

            // Create unique temp directory for user data
            String tempDir = System.getProperty("java.io.tmpdir");
            String userDataDir = tempDir + File.separator + "chrome-user-data-" + UUID.randomUUID();
            new File(userDataDir).mkdirs();

            // Basic Chrome options
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-notifications");
            options.addArguments("--start-maximized");

            // Set unique user data directory
            options.addArguments("--user-data-dir=" + userDataDir);

            // Additional CI-specific options
            if (System.getenv("CI") != null) {
                options.addArguments("--headless=new");
                options.addArguments("--window-size=1920,1080");
                options.addArguments("--disable-setuid-sandbox");
                options.addArguments("--disable-infobars");
                options.addArguments("--disable-logging");
                options.addArguments("--disable-popup-blocking");
            }

            try {
                driver = new ChromeDriver(options);
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
                driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
                driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
                wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            } catch (Exception e) {
                System.err.println("Failed to initialize WebDriver: " + e.getMessage());
                throw e;
            }
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.err.println("Error during driver quit: " + e.getMessage());
            } finally {
                driver = null;
            }
        }
    }
}