package com.naukri.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage{
    //private WebDriver driver;

    public HomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        //PageFactory.initElements(driver, this);
    }

    //Locators
    private By viewProfileLink = By.xpath("//div[@class='view-profile-wrapper']//a[contains(text(), 'View')] | //a[normalize-space()='Complete profile']");
    private By homePageTitle = By.xpath("//title");
    WebElement homePageTitleElement;
    @FindBy(xpath = "//button[span[text()='Get Power Profile']]")
    WebElement viewProfileLinkElement;


    public void clickViewProfile() {
        /*staticSleeper(1);
        //viewProfileLinkElement.click();
        WebElement profile = driver.findElement(viewProfileLink);
        //profile.click();
        clickElement(driver, profile, "Click on View Profile link", "Failed to click on View Profile link");
        //clickOnElementJS(driver, viewProfileLinkElement, "Click on View Profile link", "Failed to click on View Profile link");
        staticSleeper(3);
        logger.info("Clicked on View Profile link");*/
        try {
            org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(20));
            org.openqa.selenium.WebElement profile = wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(viewProfileLink));
            clickElement(driver, profile, "Click on View Profile link", "Failed to click on View Profile link");
            staticSleeper(3);
            logger.info("Clicked on View Profile link");
        } catch (org.openqa.selenium.TimeoutException e) {
            logger.error("View Profile link not found. Capturing page source for debugging.");
            try {
                java.nio.file.Files.write(java.nio.file.Paths.get("target/page_source_on_failure.html"), driver.getPageSource().getBytes());
            } catch (Exception ex) {
                logger.error("Failed to write page source", ex);
            }
            throw e;
        }
    }

    public void verifyHomePageLoaded() {
        WebElement homePageTitleElement = driver.findElement(homePageTitle);
        logger.info("Title of current page :::" + homePageTitleElement.getText());
        if (isElementDisplayed(homePageTitleElement)) {
            logger.info("Home page loaded successfully." + homePageTitleElement.getText());
        } else {
            logger.warn("Home page did not load successfully.");
        }
    }
}
