package com.naukri.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.naukri.stepdefinitions", "com.naukri.hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "com.naukri.utils.ExtentCucumberListener",
                "com.naukri.utils.StepLogger"
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
}