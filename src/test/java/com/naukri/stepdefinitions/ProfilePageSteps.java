package com.naukri.stepdefinitions;


import com.naukri.pages.HomePage;
import com.naukri.pages.ProfilePgae;
import com.naukri.utils.WebDriverManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ProfilePageSteps {
    private ProfilePgae oPP;

    public ProfilePageSteps() {
        WebDriver driver = WebDriverManager.getDriver();
        oPP = new ProfilePgae(driver);
    }

    @Then("I should see my profile page")
    public void iShouldSeeMyProfilePage() throws Throwable{
        oPP.isProfilePageDisplayed();

        //oPP.printSkills();
    }

    @And("I navigate to the resume management section")
    public void iNavigateToTheResumeManagementSection() throws Throwable {

    }

    @When("I remove the already uploaded resume")
    public void iRemoveTheAlreadyUploadedResume() throws Throwable {
        oPP.deleteResume();
    }

    @And("I upload my resume again")
    public void iUploadMyResumeAgain() throws Throwable {
        oPP.selectResume();
        oPP.printResumeHeadline();
    }
}
