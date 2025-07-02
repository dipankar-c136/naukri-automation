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
    protected WebDriverWait wait;

    protected WebDriver getDriver() {
        return com.naukri.utils.WebDriverManager.getDriver();
    }

    @BeforeClass
    public void setUp() {
        if (com.naukri.utils.WebDriverManager.getDriver() == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();

            if (System.getenv("CI") != null) {
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--window-size=1920,1080");
            }

            WebDriver driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
            wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            com.naukri.utils.WebDriverManager.setDriver(driver);
        }
    }

    @AfterClass
    public void tearDown() {
        com.naukri.utils.WebDriverManager.removeDriver();
    }
}