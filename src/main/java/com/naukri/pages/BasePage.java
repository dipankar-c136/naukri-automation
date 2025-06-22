package com.naukri.pages;

import com.naukri.base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage extends BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    public static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public BasePage() {
    }

    public WebDriver getDriver() {
        if (driver == null) {
            logger.error("WebDriver is not initialized in BasePage!");
            throw new IllegalStateException("WebDriver is not initialized.");
        }
        return driver;
    }

    public By element(String IdentifierString, String locateBy) {
        By by = null;
        switch (locateBy.trim().toUpperCase()) {
            case "ID":
                return By.id(IdentifierString);
            case "NAME":
                return By.name(IdentifierString);
            case "XPATH":
                return By.xpath(IdentifierString);
            case "CSS":
                return By.cssSelector(IdentifierString);
            case "CLAS":
                return By.className(IdentifierString);
            case "TAG NAME":
                return By.tagName(IdentifierString);
            default:
                return By.xpath(IdentifierString);
        }
    }

    public void fluentWait(WebDriver driver, WebElement element){
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
    }

    public WebElement fluentWait(WebDriver driver, String IdentifierString, String IdentifyBy) {
       FluentWait<WebDriver> wait = new FluentWait<>(driver);
       wait.withTimeout(Duration.ofSeconds(10))
               .pollingEvery(Duration.ofMillis(500))
               .ignoring(NoSuchElementException.class)
               .ignoring(StaleElementReferenceException.class);
       return (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(this.element(IdentifierString, IdentifyBy)));
    }

    public void staticSleeper(int Seconds) {
        try {
            Thread.sleep(Seconds * 1000L);
            logger.info("Static sleep for " + Seconds + " seconds completed.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Sleep interrupted: " + e.getMessage());
        }
    }

    // 1. Find element
    protected WebElement find(By locator) {
        return driver.findElement(locator);
    }

    // 2. Click element
    public WebElement clickElement(WebDriver driver, WebElement element, String successMSG, String failMSG) {
        WebElement web = null;
        boolean found = false;
        long startTime = System.currentTimeMillis();
        int retryCount = 0;
        int maxRetries = 5; // You can adjust the number of retries

        while (System.currentTimeMillis() - startTime < 15000L && retryCount < maxRetries) { // 15 seconds timeout
            try {
                this.fluentWait(driver, element);
                element.click();
                found = true;
                logger.info(successMSG);
                web = element;
                break;
            } catch (StaleElementReferenceException | ElementClickInterceptedException | NoSuchElementException e) {
                retryCount++;
                logger.info("Element not clickable, retrying... Attempt: " + retryCount);
                try {
                    Thread.sleep(1000); // Wait 1 second before retrying
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    logger.error("Retry interrupted: " + ie.getMessage());
                    break;
                }
            } catch (Exception e) {
                logger.error("Unexpected error during click: " + e.getMessage());
                break;
            }
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        if (found) {
            logger.info("Element clicked successfully in " + totalTime + " milliseconds.");
        } else {
            logger.error(failMSG);
            throw new RuntimeException("Element not clickable after retries: " + failMSG);
        }
        return web;
    }

    public WebElement clickOnElementJS(WebDriver driver, WebElement element, String successMSG, String failMSG) {
        WebElement web = null;
        boolean found = false;
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < 15000L) { // 15 seconds timeout
            try {
                this.fluentWait(driver, element);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                found = true;
                logger.info(successMSG);
                web = element;
                break;
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                logger.info("Element not clickable via JS, retrying...");
            } catch (Exception e) {
                logger.error("Unexpected error during JS click: " + e.getMessage());
            }
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        if (found) {
            logger.info("Element clicked via JS successfully in " + totalTime + " milliseconds.");
        } else {
            logger.error(failMSG);
            throw new RuntimeException("Element not clickable via JS after 15 seconds: " + failMSG);
        }
        return web;
    }

    // 3. Type text
    protected void setElement(WebDriver driver, String WebElementLocator, String WebElementIdentifier, String text) {
        this.fluentWait(driver, WebElementLocator, WebElementIdentifier).sendKeys(new CharSequence[]{text});
    }

    // 4. Get text
    public String getElementText(WebDriver driver, WebElement element){
        this.staticSleeper(3);
        this.fluentWait(driver, element);
        return element.getText();
    }

    public String getElementText(WebDriver driver, String IdentifierString, String IdentifyBy) {
        return this.fluentWait(driver, IdentifierString, IdentifyBy).getText();
    }

    // 5. Wait for element to be visible
    protected void waitForElementVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    // 6. Check if element is displayed
    protected boolean isElementDisplayed(WebElement element) {
        try {
            return (element.isDisplayed());
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // 7. Navigate to URL
    public void navigateTo(String url) {
        driver.get(url);
    }

    // 8. Get page title
    public String getTitle() {
        return driver.getTitle();
    }

    // 9. Get current URL
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // 10. Wait for element to be clickable
    protected void waitForElementToBeClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
}
