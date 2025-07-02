package com.naukri.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import java.time.Duration;

public class BaseTest {
    protected static WebDriver driver;
    protected static WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();

            if (System.getenv("CI") != null) {
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--window-size=1920,1080");
                options.addArguments("--disable-gpu");
                options.addArguments("--disable-notifications");
                options.addArguments("--remote-allow-origins=*");
            }

            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
            driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
            wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}