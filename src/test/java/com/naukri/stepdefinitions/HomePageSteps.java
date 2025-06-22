package com.naukri.stepdefinitions;

import com.naukri.pages.HomePage;
import com.naukri.utils.WebDriverManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class HomePageSteps {

    //private WebDriver driver;
    private HomePage homePage;

    public HomePageSteps() {
        WebDriver driver = WebDriverManager.getDriver();
        homePage = new HomePage(driver);
    }

    @And("I clicked on the <View Profile> button")
    public void iClickedOnTheViewProfileButton() throws Throwable{
        //startStep();
        //driver = new ChromeDriver();
        homePage.verifyHomePageLoaded();
        homePage.clickViewProfile();
        //endStep();
    }

}
