package com.naukri.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {
    private WebDriver driver;

    // Locators
    private By usernameField = By.id("usernameField");
    private By passwordField = By.id("passwordField");
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