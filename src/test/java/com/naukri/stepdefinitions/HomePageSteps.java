package com.naukri.stepdefinitions;

import com.naukri.base.BaseTest;
import com.naukri.pages.HomePage;
import io.cucumber.java.en.And;
import org.openqa.selenium.WebDriver;

public class HomePageSteps extends BaseTest {

    //private WebDriver driver;
    private final HomePage homePage;

    /*public HomePageSteps() {
        WebDriver driver = WebDriverManager.getDriver();
        homePage = new HomePage(driver);
    }*/

    public HomePageSteps() {
        homePage = new HomePage(getDriver());
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
