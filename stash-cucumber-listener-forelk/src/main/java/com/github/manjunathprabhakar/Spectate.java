package com.github.manjunathprabhakar;

import io.cucumber.messages.types.GherkinDocument;
import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.*;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Spectate implements EventListener {

    private final Map<URI, GherkinDocument> eachGherkinDocFeature = new LinkedHashMap<>();


    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestSourceRead.class, this::readGherkinSource);
        eventPublisher.registerHandlerFor(TestCaseStarted.class, this::scenarioStarted);
        eventPublisher.registerHandlerFor(EmbedEvent.class, this::logEmbeds);
        eventPublisher.registerHandlerFor(WriteEvent.class, this::logWrites);
        eventPublisher.registerHandlerFor(TestStepFinished.class, this::gherkinStepFinished);
        eventPublisher.registerHandlerFor(TestCaseFinished.class, this::scenarioFinished);
        eventPublisher.registerHandlerFor(TestRunFinished.class, this::wrapUp);
    }

    private void readGherkinSource(TestSourceRead testSourceRead) {

    }

    private void scenarioStarted(TestCaseStarted testCaseStarted) {

    }

    private void logEmbeds(EmbedEvent embedEvent) {

    }

    private void logWrites(WriteEvent writeEvent) {

    }

    private void gherkinStepFinished(TestStepFinished testStepFinished) {

    }

    private void scenarioFinished(TestCaseFinished testCaseFinished) {

    }

    private void wrapUp(TestRunFinished testRunFinished) {


        //Call and log to ELK
    }


    private List<String> mergeScenarioStatuses(List<String> stashoutPOJO,
                                               List<String> scenarioStatusesPOJO) {
        return stashoutPOJO;
    }

    private String convertToCamelCaseWhileOnlyRetainingCharsAndNumbers(String mediaType) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean nextUcase = false;

        for (char c : mediaType.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                if (nextUcase) {
                    stringBuilder.append(Character.toUpperCase(c));
                    nextUcase = false;
                } else
                    stringBuilder.append(Character.toLowerCase(c));
            } else
                nextUcase = true;
        }

        return stringBuilder.toString();
    }
}