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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriverWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
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
        ChromeOptions options = new ChromeOptions();

        // Basic headless configuration
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");

        // Window and performance settings
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--remote-allow-origins=*");

        // Additional stability options
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-notifications");

        try {
            io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
            //driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driver.manage().window().maximize();
            com.naukri.utils.WebDriverManager.setDriver(driver);
        } catch (Exception e) {
            System.err.println("Failed to initialize WebDriver: " + e.getMessage());
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
        driver.get("https://www.naukri.com/nlogin/login");
        loginPage = new LoginPage(driver);
        driver.manage().window().maximize();

        // Wait for the page to be fully loaded
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));

        // Wait a bit longer to ensure all dynamic content is loaded
        Thread.sleep(5000);
    }

    @When("I enter my credentials and login")
    public void i_enter_my_credentials_and_login() throws Throwable {
        try {
            // Wait for username field to be present and visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("usernameField")));

            loginPage.enterUsername(username);
            Thread.sleep(1000);

            // Wait for password field
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("passwordField")));
            loginPage.enterPassword(password);
            Thread.sleep(1000);

            // Wait for login button
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-primary"))); // adjust selector as needed
            loginPage.clickLoginButton();
            Thread.sleep(2000);
        } catch (Exception e) {
            System.err.println("Failed to interact with login form: " + e.getMessage());
            // Take screenshot for debugging
            if (driver instanceof TakesScreenshot) {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                Files.copy(screenshot.toPath(), new File("login-error-screenshot.png").toPath());
            }
            throw e;
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
