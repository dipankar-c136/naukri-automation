package com.naukri.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "classpath:features",  // Changed path
        glue = {"com.naukri.stepdefinitions"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-pretty.html",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "tech.grasshopper.reporter.ExtentCucumber7Report"  // Updated plugin
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
}