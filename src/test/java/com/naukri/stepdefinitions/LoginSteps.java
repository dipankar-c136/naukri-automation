package com.naukri.stepdefinitions;

import com.naukri.base.BaseTest;
import com.naukri.pages.LoginPage;
import com.naukri.utils.ExcelUtils;
import com.naukri.utils.WebDriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import java.io.IOException;
import java.time.Duration;
//import org.openqa.selenium.WebDriverWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.nio.file.Files;
import static com.naukri.utils.StepLogger.*;
import org.openqa.selenium.chrome.ChromeOptions;

public class LoginSteps extends BaseTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private String username;
    private String password;

    @Before
    public void setUp() {
        try {
            // Setup WebDriverManager
            io.github.bonigarcia.wdm.WebDriverManager.chromedriver()
                    .clearDriverCache()
                    .clearResolutionCache()
                    .setup();

            // Configure Chrome options
            ChromeOptions options = new ChromeOptions();

            // Common options
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-extensions");

            // CI-specific options
            if (System.getenv("CI") != null) {
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");

                // Create temp directory with full permissions
                String tempDir = "/tmp/chrome-data-" + System.currentTimeMillis();
                File chromeDataDir = new File(tempDir);
                chromeDataDir.mkdirs();
                chromeDataDir.setReadable(true, false);
                chromeDataDir.setWritable(true, false);
                chromeDataDir.setExecutable(true, false);
                options.addArguments("--user-data-dir=" + tempDir);
            }

            // Set Chrome binary path in CI environment
            if (System.getenv("CI") != null) {
                options.setBinary("/usr/bin/google-chrome");
            }

            // Set system properties
            System.setProperty("webdriver.http.factory", "jdk-http-client");
            String chromeDriverPath = System.getenv("CHROMEDRIVER_PATH");
            if (chromeDriverPath != null && !chromeDriverPath.isEmpty()) {
                System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            }

            // Initialize driver with options
            driver = new ChromeDriver(options);

            // Configure timeouts and window
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driver.manage().window().maximize();

            // Set driver in WebDriverManager
            com.naukri.utils.WebDriverManager.setDriver(driver);

        } catch (Exception e) {
            System.err.println("Failed to initialize WebDriver: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Given("I have the Naukri login credentials from the Excel file")
    public void i_have_the_naukri_login_credentials() throws Throwable {
        //startStep();
        /*ExcelUtils excelUtils = new ExcelUtils();
        username = excelUtils.readExcelData("Sheet1", 1, 1); // Assuming username is in the first row, first column
        password = excelUtils.readExcelData("Sheet1", 2, 1); // Assuming password is in the first row, second column*/
        String excelFilePath = "src/main/resources/testdata.xlsx"; // Update with actual path
        ExcelUtils excelUtils = new ExcelUtils(excelFilePath);
        String[][] data = excelUtils.readExcelData("Sheet1");
        username = data[0][0]; // First row, first column
        password = data[0][1]; // First row, second column
        System.out.println("DEBUG: Username from Excel: " + username);
        System.out.println("DEBUG: Password from Excel: " + password);
        //endStep();
    }

    /*@And("I open the Naukri login page")
    public void i_open_the_naukri_login_page() throws Throwable {
        driver.get("https://www.naukri.com/nlogin/login"); // Naukri login URL
        loginPage = new LoginPage(driver);
        driver.manage().window().maximize(); // Maximize the browser window
        Thread.sleep(3000); // Wait for the page to load

    }

    @When("I enter my credentials and login")
    public void i_enter_my_credentials_and_login() throws Throwable {
        //startStep();
        loginPage.enterUsername(username);
        Thread.sleep(1000);
        loginPage.enterPassword(password);
        Thread.sleep(1000);
        loginPage.clickLoginButton();
        Thread.sleep(2000); // Wait for login to complete
        //loginPage.navigateToResumeManagement();
        //endStep();
    }*/

    @And("I open the Naukri login page")
    public void i_open_the_naukri_login_page() throws Throwable {
        try {
            // Open URL with retry mechanism
            int maxRetries = 3;
            for (int i = 0; i < maxRetries; i++) {
                try {
                    driver.get("https://www.naukri.com/nlogin/login");
                    break;
                } catch (Exception e) {
                    if (i == maxRetries - 1) throw e;
                    Thread.sleep(2000);
                }
            }

            // Wait for page load
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").equals("complete"));

            // Additional wait for dynamic content
            Thread.sleep(5000);
        } catch (Exception e) {
            System.err.println("Failed to load page: " + e.getMessage());
            takeScreenshot("page-load-error");
            throw e;
        }
    }

    @When("I enter my credentials and login")
    public void i_enter_my_credentials_and_login() throws Throwable {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            // Wait and verify login form is visible
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("usernameField")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("usernameField")));

            // Use JavaScript to enter credentials
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.getElementById('usernameField').value=arguments[0]", username);
            Thread.sleep(1000);
            js.executeScript("document.getElementById('passwordField').value=arguments[0]", password);
            Thread.sleep(1000);

            // Click login using JavaScript
            WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[text()='Login']")));
            js.executeScript("arguments[0].click();", loginBtn);

            Thread.sleep(3000);
        } catch (Exception e) {
            System.err.println("Login failed: " + e.getMessage());
            takeScreenshot("login-error");
            throw e;
        }
    }

    private void takeScreenshot(String name) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(),
                    new File("target/screenshots/" + name + "-" + System.currentTimeMillis() + ".png").toPath());
        } catch (Exception e) {
            System.err.println("Screenshot failed: " + e.getMessage());
        }
    }

    @Then("I remove the uploaded resume")
    public void i_remove_the_uploaded_resume() {
        // Logic to navigate to resume management and remove the uploaded resume
    }

    @Then("I upload a new resume")
    public void i_upload_a_new_resume() {
        // Logic to upload the resume
    }

    @Then("I verify the resume upload is successful")
    public void i_verify_the_resume_upload_is_successful() {
        // Logic to verify the resume upload
        Assert.assertTrue(true); // Replace with actual verification logic
    }

    @Then("I close the browser")
    public void i_close_the_browser() {
        driver.quit();
    }

    @After
    public void tearDown() {
        WebDriverManager.quitDriver();
    }
}
