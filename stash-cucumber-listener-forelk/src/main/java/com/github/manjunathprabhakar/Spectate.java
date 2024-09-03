package com.github.manjunathprabhakar;

import com.github.manjunathprabhakar.constants.For;
import com.github.manjunathprabhakar.constants.Format;
import com.github.manjunathprabhakar.pojos.out.StashOutPojos;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.gson.Gson;
import io.cucumber.gherkin.GherkinParser;
import io.cucumber.messages.types.Envelope;
import io.cucumber.messages.types.GherkinDocument;
import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.*;
import lombok.SneakyThrows;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

public final class Spectate implements EventListener {

    private final Map<URI, GherkinDocument> eachGherkinDocFeature = new LinkedHashMap<>();
    private final Map<String, Integer> logAttachmentsCount = new LinkedHashMap<>();
    private final List<RunTimeScenarioStatuses> runTimeScenarioStatuses = new LinkedList<>();
    private final List<StashOutPojos> finalResults = new LinkedList<>();
    private StashOutPojos stashOutPojos;
    private int _RUNID = new Random().nextInt();
    private long scenarioDurationInNanos = 0;


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


    @SneakyThrows
    private void readGherkinSource(TestSourceRead testSourceRead) {
        URI uri = testSourceRead.getUri();
        GherkinParser build = GherkinParser.builder().build();
        Stream<Envelope> parse = build.parse(Paths.get(uri));
        parse.map(Envelope::getGherkinDocument).filter(Optional::isPresent)
                .map(Optional::get).findFirst().ifPresent(gd -> eachGherkinDocFeature.put(uri, gd));
    }

    private void scenarioStarted(TestCaseStarted tcStarted) {
        scenarioDurationInNanos = 0;
        stashOutPojos = new StashOutPojos();
        String featureName = "UNNAMED";
        if (eachGherkinDocFeature.containsKey(tcStarted.getTestCase().getUri())) {
            featureName = eachGherkinDocFeature.get(tcStarted.getTestCase().getUri()).getFeature().get().getName();
            //_RUNID = eachGherkinDocFeature.get(tcStarted.getTestCase().getUri()).getFeature().hashCode();
            _RUNID =
                    (/*tcStarted.getTestCase().getUri() +*/

                            eachGherkinDocFeature.get(tcStarted.getTestCase().getUri()).getFeature().get().getName())
                            .hashCode();
        }
        stashOutPojos.setFeatureIndex(Math.abs(_RUNID));
        stashOutPojos.setFeatureURI((tcStarted.getTestCase().getUri()).toString());
        stashOutPojos.setFeatureName(featureName);

        stashOutPojos.setScenarioStartDate(DateTimeFormatter.ofPattern(Format.DATE.getFormat()).withZone(ZoneOffset.UTC).format(tcStarted.getInstant()));
        stashOutPojos.setScenarioStartTime(DateTimeFormatter.ofPattern(Format.TIME.getFormat()).withZone(ZoneOffset.UTC).format(tcStarted.getInstant()));

        //stashOutPojos.setScenarioIndex(tcStarted.getTestCase().getId().toString());
        stashOutPojos.setScenarioIndex(
                Math.abs((eachGherkinDocFeature.get(tcStarted.getTestCase().getUri()).getFeature().get().getName() + tcStarted.getTestCase().getName()).hashCode())
                        + ""
        );
        stashOutPojos.setScenarioName(tcStarted.getTestCase().getName());
        stashOutPojos.setScenarioTags(tcStarted.getTestCase().getTags());
    }

    private void logEmbeds(EmbedEvent embedEvent) {
        String mediaType = convertToCamelCaseWhileOnlyRetainingCharsAndNumbers(embedEvent.getMediaType());
        if (logAttachmentsCount.containsKey(mediaType))
            logAttachmentsCount.put(mediaType, logAttachmentsCount.get(mediaType) + 1);
        else
            logAttachmentsCount.put(mediaType, 1);
    }

    private void logWrites(WriteEvent writeEvent) {
        String mediaType = convertToCamelCaseWhileOnlyRetainingCharsAndNumbers("text/plain");
        if (logAttachmentsCount.containsKey(mediaType))
            logAttachmentsCount.put(mediaType, logAttachmentsCount.get(mediaType) + 1);
        else
            logAttachmentsCount.put(mediaType, 1);
    }

    private void gherkinStepFinished(TestStepFinished testStepFinished) {
        if (testStepFinished.getTestStep() instanceof PickleStepTestStep) {
            stashOutPojos.setTotalSteps(stashOutPojos.getTotalSteps() + 1);
            if (testStepFinished.getResult().getStatus().toString().equalsIgnoreCase("passed"))
                stashOutPojos.setTotalStepsPass(stashOutPojos.getTotalStepsPass() + 1);
            else if (testStepFinished.getResult().getStatus().toString().equalsIgnoreCase("failed"))
                stashOutPojos.setTotalStepsFail(stashOutPojos.getTotalStepsFail() + 1);
            else if (testStepFinished.getResult().getStatus().toString().equalsIgnoreCase("skipped"))
                stashOutPojos.setTotalStepsSkip(stashOutPojos.getTotalStepsSkip() + 1);
            else
                stashOutPojos.setTotalStepsOtherStatus(stashOutPojos.getTotalStepsOtherStatus() + 1);
        }
        scenarioDurationInNanos = scenarioDurationInNanos + testStepFinished.getResult().getDuration().toNanos();
    }

    private void scenarioFinished(TestCaseFinished testCaseFinished) {
        //stashOutPojos.setScenarioDurationInNanos(testCaseFinished.getResult().getDuration().isZero() ? 0 : testCaseFinished.getResult().getDuration().toNanos());
        stashOutPojos.setScenarioDurationInNanos(scenarioDurationInNanos);
        stashOutPojos.setScenarioStatus(testCaseFinished.getResult().getStatus().toString());
        StringWriter stringWriter = new StringWriter();
        if (testCaseFinished.getResult().getError() != null)
            try (PrintWriter printWriter = new PrintWriter(stringWriter)) {
                testCaseFinished.getResult().getError().printStackTrace(printWriter);
            }
        stashOutPojos.setScenarioError(stringWriter.toString().isEmpty() ? "NA" : stringWriter.toString());

        runTimeScenarioStatuses.add(
                RunTimeScenarioStatuses.builder()
                        .featureURI(testCaseFinished.getTestCase().getUri())
                        .featureName(stashOutPojos.getFeatureName())
                        .scenarioName(stashOutPojos.getScenarioName())
                        .scenarioStatus(testCaseFinished.getResult().getStatus().toString())
                        .build()
        );

        stashOutPojos.setScenarioLogsCount(logAttachmentsCount);

        stashOutPojos.set_dataThrough(For.CUKE_LISTENER.name());
        stashOutPojos.set_loadDate(getCurrentDate(Format.AUDIT_DATE.getFormat()));
        stashOutPojos.set_loadTime(getCurrentDate(Format.AUDIT_TIME.getFormat()));
        String machineName = "UNKNOWN";
        try {
            machineName = InetAddress.getLocalHost().getHostName();
        } catch (Exception ignored) {
        }
        stashOutPojos.set_machineName(machineName);
        stashOutPojos.set_userName(System.getProperty("user.name").toUpperCase(Locale.ROOT));

        finalResults.add(stashOutPojos);
    }

    private void wrapUp(TestRunFinished testRunFinished) {
        List<StashOutPojos> stashOutPojosList = mergeScenarioStatuses(finalResults, runTimeScenarioStatuses);

        for (StashOutPojos outPojos : stashOutPojosList) {
            //Call and log to ELK
            outPojos.setFeatureURI(relativize(URI.create(outPojos.getFeatureURI())).toString());
        }


        System.out.println("Spectate: " + new Gson().toJson(stashOutPojosList));
    }


    private List<StashOutPojos> mergeScenarioStatuses(List<StashOutPojos> stashoutPOJO,
                                                      List<RunTimeScenarioStatuses> runTimeScenarioStatusesPOJO) {

        Table<URI, String, List<String>> listTable = HashBasedTable.create();
        for (RunTimeScenarioStatuses scenarioStatusData : runTimeScenarioStatusesPOJO) {
            String featureName = scenarioStatusData.getFeatureName();
            URI featureURI = scenarioStatusData.getFeatureURI();
            String scenarioStatus = scenarioStatusData.getScenarioStatus();

            if (!listTable.contains(featureURI, featureName))
                listTable.put(featureURI, featureName, Collections.singletonList(scenarioStatus));
            else {
                List<String> allStatuses = listTable.get(featureURI, featureName);
                LinkedList<String> availableStatuses = new LinkedList<>(allStatuses);
                availableStatuses.add(scenarioStatus);
                listTable.put(featureURI, featureName, availableStatuses);
            }
        }

        Map<URI, Map<String, List<String>>> uriMapMap = listTable.rowMap();
        for (Map.Entry<URI, Map<String, List<String>>> uriMapEntry : uriMapMap.entrySet()) {
            URI key = uriMapEntry.getKey();
            Map<String, List<String>> value = uriMapEntry.getValue();
            for (Map.Entry<String, List<String>> stringListEntry : value.entrySet()) {
                String key1 = stringListEntry.getKey();
                List<String> value1 = stringListEntry.getValue();
                String featureResult = "OTHER";
                if (value1.stream().anyMatch(a -> a.equalsIgnoreCase("failed")))
                    featureResult = "FAILED";
                if (value1.stream().allMatch(a -> a.equalsIgnoreCase("passed")))
                    featureResult = "PASSED";
                if (value1.stream().anyMatch(a -> a.equalsIgnoreCase("skipped")))
                    featureResult = "SKIPPED";

                long totalCount = value1.size();
                long totalPass = value1.stream().filter(a -> a.equalsIgnoreCase("passed")).count();
                long totalFail = value1.stream().filter(a -> a.equalsIgnoreCase("failed")).count();
                long totalSkip = value1.stream().filter(a -> a.equalsIgnoreCase("skipped")).count();
                long totalother = totalCount - (totalPass - totalFail - totalSkip);

                for (StashOutPojos outPojos : stashoutPOJO) {
                    if (outPojos.getFeatureURI().equalsIgnoreCase(key.toString())
                            && outPojos.getFeatureName().equalsIgnoreCase(key1)) {
                        outPojos.setTotalScenarios(totalCount);
                        outPojos.setTotalPassedScenarios(totalPass);
                        outPojos.setTotalFailedScenarios(totalFail);
                        outPojos.setTotalSkippedScenarios(totalSkip);
                        outPojos.setTotalOtherStatusScenarios(totalother);
                        outPojos.setFeatureStatus(featureResult.toUpperCase(Locale.ROOT));
                    }
                }
            }
        }

        return stashoutPOJO;
    }


    private String getCurrentDate(String foramt) {
        Date time = Calendar.getInstance().getTime();
        return new SimpleDateFormat(foramt).format(time);
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

    private static URI relativize(URI uri) {
        if (!"file".equals(uri.getScheme())) {
            return uri;
        } else if (!uri.isAbsolute()) {
            return uri;
        } else {
            try {
                URI root = (new File("")).toURI();
                URI relative = root.relativize(uri);
                return new URI("file", relative.getSchemeSpecificPart(), relative.getFragment());
            } catch (URISyntaxException var3) {
                URISyntaxException e = var3;
                throw new IllegalArgumentException(e.getMessage(), e);
            }
        }
    }
}