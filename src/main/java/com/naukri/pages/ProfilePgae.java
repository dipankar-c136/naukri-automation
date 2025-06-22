package com.naukri.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProfilePgae extends BasePage {
    private final WebDriver driver;

    // Example locator for profile name (update as per actual DOM)
    private By profilePageTitle = By.xpath("//title");
    @FindBy(xpath = "//input[@value='Update resume'] | //span[@class='dummyUploadNewCTA']")
    WebElement updateresumeButton;
    @FindBy(xpath = "//i[@title='Click here to delete your resume']")
    WebElement deleteResumeButton;
    @FindBy(xpath = "//p[contains(text(), 'Are you sure you want to delete the resume?')]")
    WebElement deleteResumeConfirmationMessage;
    @FindBy(xpath = "(//button[contains(text(), 'Delete')])[3]")
    WebElement deleteResumeConfirmationButton;
    @FindBy(xpath = "//div[@id='lazyResumeHead']")
    WebElement resumeHeadline;

    public ProfilePgae(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Example method to verify profile page is loaded
    public void isProfilePageDisplayed() throws Throwable{
        WebElement profilePageTitleElement = driver.findElement(profilePageTitle);
        logger.info("Title of current page :::" + getTitle());
        String titleText = getTitle();
        if(titleText == "Profile | Mynaukri") {
            logger.info("Profile page is displayed." + profilePageTitleElement.getText());
        } else {
            logger.warn("Profile page is not displayed.");
        }
    }

    public void deleteResume() throws Throwable{
        staticSleeper(1);
        new Actions(driver).scrollToElement(deleteResumeButton).perform();
        staticSleeper(1);
        deleteResumeButton.click();
        logger.info("Resume delete button clicked.");
        // Wait for the confirmation dialog to appear
        staticSleeper(2);
        // Assuming there's a confirmation dialog that appears after clicking delete
        logger.info("Waiting for delete confirmation message to appear...");
        logger.info("Delete confirmation message: " + deleteResumeConfirmationMessage.getText());
        logger.info("Clicking on delete confirmation button...");
        deleteResumeConfirmationButton.click();
        staticSleeper(3);
    }

    public void selectResume() throws Throwable{
        staticSleeper(1);
        //waitForElementToBeClickable(updateresumeButton); --> this method is not working as expected, so using direct click
        new Actions(driver).scrollToElement(updateresumeButton).perform();
        staticSleeper(1);
        //updateresumeButton.click();
        //logger.info("Resume update button clicked.");
        // Locate the file input element (update the locator if needed)
        WebElement fileInput = driver.findElement(By.xpath("//input[@type='file']"));

        // Get the absolute path of the resume in resources
        String resumePath = new File("src/main/resources/CV_Dipankar_Chakraborty_2025.pdf").getAbsolutePath();

        // Upload the file
        fileInput.sendKeys(resumePath);
        logger.info("Resume uploaded from: " + resumePath);

        staticSleeper(5);
    }

    public void printResumeHeadline() throws Throwable {
        staticSleeper(1);
        // Assuming there's a headline element on the profile page
        logger.info("Resume Headline: " + resumeHeadline.getText());
    }

    public void printSkills() throws Throwable {
        new Actions(driver).scrollToElement(driver.findElement(By.xpath("//span[@class='widgetTitle'][normalize-space()='IT skills']"))).perform();

        // Locate the skills widget
        WebElement skillsWidget = driver.findElement(By.xpath("//span[text()='IT skills']/ancestor::div[contains(@class,'widgetHead')]/following-sibling::div[contains(@class,'widgetCont')]"));

        // Get column headers
        List<WebElement> headers = skillsWidget.findElements(By.xpath(".//ul/li[1]/span[starts-with(@class,'col')]"));
        List<String> columnNames = new ArrayList<>();
        for (WebElement header : headers) {
            String colName = header.getText().trim();
            if (!colName.isEmpty()) {
                columnNames.add(colName);
            }
        }

        // Get all skill rows (excluding header)
        List<WebElement> skillRows = skillsWidget.findElements(By.xpath(".//ul/li[position()>1 and contains(@class,'collection')]"));

        for (WebElement row : skillRows) {
            List<WebElement> cols = row.findElements(By.xpath("./span[starts-with(@class,'col') and not(contains(@class,'icon'))]"));
            for (int i = 0; i < cols.size() && i < columnNames.size(); i++) {
                System.out.println(columnNames.get(i) + ": " + cols.get(i).getText().trim());
            }
            System.out.println("-----");
        }
    }
}

