package com.naukri.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    public void enterUsername(String username) {
        WebElement usernameElement = driver.findElement(usernameField);
        usernameElement.clear();
        usernameElement.sendKeys(username);
        logger.info("Entered username: " + username);
    }

    public void enterPassword(String password) {
        WebElement passwordElement = driver.findElement(passwordField);
        passwordElement.clear();
        passwordElement.sendKeys(password);
        logger.info("Entered password: " + password);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
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