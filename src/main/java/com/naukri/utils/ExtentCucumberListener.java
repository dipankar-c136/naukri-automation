package com.naukri.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.*;

public class ExtentCucumberListener implements EventListener {

    private ExtentReports extent = ExtentReportManager.getInstance();
    private ExtentTest featureTest;
    private ExtentTest scenarioTest;
    private ExtentTest stepTest;

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, this::handleTestCaseStart);
        publisher.registerHandlerFor(TestStepStarted.class, this::handleStepStart);
        publisher.registerHandlerFor(TestStepFinished.class, this::handleStepFinish);
        publisher.registerHandlerFor(TestCaseFinished.class, this::handleTestCaseFinish);
    }

    private void handleTestCaseStart(TestCaseStarted event) {
        featureTest = extent.createTest(event.getTestCase().getUri().toString());
        scenarioTest = featureTest.createNode(event.getTestCase().getName());
    }

    private void handleStepStart(TestStepStarted event) {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
            String stepText = step.getStep().getKeyword() + step.getStep().getText();
            stepTest = scenarioTest.createNode(stepText);
            System.out.println("=====> START STEP: " + stepText);
        }
    }

    private void handleStepFinish(TestStepFinished event) {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
            String stepText = step.getStep().getKeyword() + step.getStep().getText();

            if (event.getResult().getStatus() == Status.PASSED) {
                stepTest.pass("Step Passed");
            } else if (event.getResult().getStatus() == Status.FAILED) {
                stepTest.fail(event.getResult().getError());
            }
            System.out.println("=====> END STEP: " + stepText);
        }
    }

    private void handleTestCaseFinish(TestCaseFinished event) {
        extent.flush();
    }
}
