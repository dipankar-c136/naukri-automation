package com.naukri.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.time.Duration;
import java.util.Arrays;

public class BaseTest {
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected static ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();

    @BeforeMethod
    public void setUp() {
        if (driver.get() == null) {
            WebDriverManager.chromedriver().clearDriverCache().setup();
            ChromeOptions options = new ChromeOptions();

            if (System.getenv("CI") != null) {
                options.addArguments("--headless=chrome"); // Use chrome headless mode
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");
                options.addArguments("--remote-allow-origins=*");
                options.addArguments("--disable-blink-features=AutomationControlled");
                options.addArguments("--disable-infobars");
                options.addArguments("--disable-extensions");
                options.addArguments("--start-maximized");
                options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");

                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--window-size=1920,1080");
                options.addArguments("--disable-gpu");
                options.addArguments("--disable-extensions");
                options.addArguments("--disable-dev-tools");
                options.addArguments("--ignore-certificate-errors");
                options.addArguments("--remote-allow-origins=*");

                // Add these to avoid detection
                options.addArguments("--disable-blink-features=AutomationControlled");
                options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
                options.setExperimentalOption("useAutomationExtension", false);
            }

            driver.set(new ChromeDriver(options));
            wait.set(new WebDriverWait(getDriver(), Duration.ofSeconds(30)));

            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
            getDriver().manage().window().maximize();
        }
    }

    protected WebDriver getDriver() {
        return driver.get();
    }

    protected WebDriverWait getWait() {
        return wait.get();
    }

    @AfterMethod
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
            wait.remove();
        }
    }
}