package com.naukri.utils;

import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.*;

import java.time.LocalDateTime;

public class StepLogger implements EventListener {

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepStarted.class, this::handleStepStart);
        publisher.registerHandlerFor(TestStepFinished.class, this::handleStepEnd);
    }

    private void handleStepStart(TestStepStarted event) {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
            String stepText = step.getStep().getKeyword() + step.getStep().getText();
            System.out.println("=====> START STEP: " + stepText + " | " + LocalDateTime.now());
        }
    }

    private void handleStepEnd(TestStepFinished event) {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
            String stepText = step.getStep().getKeyword() + step.getStep().getText();
            System.out.println("=====> END STEP: " + stepText + " | " + LocalDateTime.now());
        }
    }
}
