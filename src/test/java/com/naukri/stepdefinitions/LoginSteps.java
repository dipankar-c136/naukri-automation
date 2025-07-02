package com.naukri.stepdefinitions;

import com.naukri.base.BaseTest;
import com.naukri.pages.LoginPage;
import com.naukri.utils.ExcelUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginSteps extends BaseTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private String username;
    private String password;

    @Before
    public void setUp() {
        /*System.setProperty("webdriver.chrome.driver", "C:\\Frameork Utils\\chromedriver.exe");
        driver = new ChromeDriver();
        WebDriverManager.setDriver(driver);*/

        /*String driverPath = System.getenv("CHROME_DRIVER_PATH");
        System.setProperty("webdriver.chrome.driver", driverPath);
        driver = new ChromeDriver();
        WebDriverManager.setDriver(driver);*/

        /*io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        com.naukri.utils.WebDriverManager.setDriver(driver);*/

        super.setUp(); // Use parent's setUp
        loginPage = new LoginPage(getDriver());
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

    @And("I open the Naukri login page")
    public void i_open_the_naukri_login_page() throws Throwable {
        /*driver.get("https://www.naukri.com/nlogin/login"); // Naukri login URL
        loginPage = new LoginPage(driver);
        driver.manage().window().maximize(); // Maximize the browser window
        Thread.sleep(3000); // Wait for the page to load*/

        getDriver().get("https://www.naukri.com/nlogin/login");
        getDriver().manage().window().maximize();
        Thread.sleep(3000);
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
        if (getDriver() != null) {
            getDriver().quit();
        }
    }

    @After
    public void tearDown() {
        /*WebDriverManager.quitDriver();*/
        super.tearDown();
    }
}