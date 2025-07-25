package com.naukri.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {
    private WebDriver driver;

    // Locators
    private By usernameField = By.cssSelector("#usernameField"); //By.xpath("//input[@id='usernameField' and @type='text']"); //By.id("usernameField");
    private By passwordField = By.xpath("//input[@id='passwordField' and @type='password']"); //By.id("passwordField");
    private By loginButton = By.xpath("//button[text()='Login']");
    private By resumeManagementLink = By.id("resumeManagement");
    private By deleteResumeButton = By.id("deleteResume");
    private By uploadResumeButton = By.id("uploadResume");
    private By uploadInputField = By.id("uploadInput");
    private By saveButton = By.id("saveButton");

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    private WebElement waitForElementPresence(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.error("Element not found: " + locator.toString(), e);
            throw e; // Re-throw the exception to fail the test
        }
    }

    public void enterUsername(String username) {
        //WebElement usernameElement = driver.findElement(usernameField);
        WebElement usernameElement = waitForElementPresence(usernameField, 20);
        usernameElement.clear();
        usernameElement.sendKeys(username);
        logger.info("Entered username: " + username);
    }

    public void enterPassword(String password) {
        //WebElement passwordElement = driver.findElement(passwordField);
        WebElement passwordElement = waitForElementPresence(passwordField, 10);
        passwordElement.clear();
        passwordElement.sendKeys(password);
        logger.info("Entered password: " + password);
    }

    public void clickLoginButton() {
        //driver.findElement(loginButton).click();
        WebElement loginButtonElement = waitForElementPresence(loginButton, 10);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement clickableLoginButton = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        clickableLoginButton.click();
        logger.info("Clicked on login button");
        // Wait for possible redirect or error
        try {
            Thread.sleep(3000); // Wait for 3 seconds for page to update
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // Log current URL and title after login attempt
        String currentUrl = driver.getCurrentUrl();
        String currentTitle = driver.getTitle();
        logger.info("URL after login attempt: " + currentUrl);
        logger.info("Title after login attempt: " + currentTitle);
        // Check if CAPTCHA is present and handle it
        try {
            if(driver.findElement(By.className("g-recaptcha")).isDisplayed()) {
                logger.warn("CAPTCHA detected - needs manual intervention");
                // Add logic to handle CAPTCHA
            }
        } catch (NoSuchElementException e) {
            // CAPTCHA not present, proceed with login
        }
        // Try to log any error message on the login page
        try {
            By errorMsgLocator = By.xpath("//*[contains(@class,'error') or contains(@class,'err') or contains(text(),'incorrect') or contains(text(),'Invalid') or contains(text(),'captcha') or contains(text(),'block')]");
            java.util.List<WebElement> errors = driver.findElements(errorMsgLocator);
            for (WebElement error : errors) {
                if (error.isDisplayed() && !error.getText().trim().isEmpty()) {
                    logger.warn("Login error message: " + error.getText().trim());
                }
            }
        } catch (Exception ex) {
            logger.warn("Could not check for login error messages.", ex);
        }
        // Warn if still on login page
        if (currentUrl.contains("/nlogin/login")) {
            logger.warn("Login failed: Still on login page after login attempt. Possible CAPTCHA, block, or invalid credentials.");
        }
        // Capture page source and screenshot for debugging
        try {
            java.nio.file.Files.write(java.nio.file.Paths.get("target/page_source_after_login.html"), driver.getPageSource().getBytes());
            org.openqa.selenium.OutputType outputType = org.openqa.selenium.OutputType.FILE;
            org.openqa.selenium.TakesScreenshot ts = (org.openqa.selenium.TakesScreenshot) driver;
            java.io.File screenshot = (java.io.File) ts.getScreenshotAs(outputType);
            java.nio.file.Files.copy(screenshot.toPath(), java.nio.file.Paths.get("target/screenshot_after_login.png"), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            logger.error("Failed to write page source or screenshot after login", ex);
        }
    }

    public void navigateToResumeManagement() {
        driver.findElement(resumeManagementLink).click();
    }

    public void deleteUploadedResume() {
        driver.findElement(deleteResumeButton).click();
    }

    public void uploadResume(String resumePath) {
        WebElement uploadElement = driver.findElement(uploadInputField);
        uploadElement.sendKeys(resumePath);
        driver.findElement(uploadResumeButton).click();
    }

    public void saveChanges() {
        driver.findElement(saveButton).click();
    }
}