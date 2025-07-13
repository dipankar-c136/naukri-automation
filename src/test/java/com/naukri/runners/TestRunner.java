package com.naukri.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "classpath:features", //"src/test/resources/features",
        glue = "com.naukri.stepdefinitions",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-pretty.html",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
                /*"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "html:target/cucumber-reports/report.html",
                "json:target/cucumber-reports/report.json"*/
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
        @Override
        @DataProvider(parallel = false)
        public Object[][] scenarios() {
                return super.scenarios();
        }
}