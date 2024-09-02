package com.github.manjunathprabhakar.core.engine;

import com.github.manjunathprabhakar.core.constants.For;
import com.github.manjunathprabhakar.core.pojos.inparser.*;
import com.github.manjunathprabhakar.core.pojos.out.StashOutPojos;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class StashParserForCukeJson {

    public static List<StashOutPojos> parse(String jsonPaths)
            throws Exception {

        //TEST DATA JSONS
        List<FeaturePOJO> jsons = Handler.getJSONsToList(jsonPaths);
        List<FeaturePOJO> featurePOJOS = Handler.combineMultipleScenariosOfSameFeature(jsons);

        //PAYLOAD
        List<StashOutPojos> results = new LinkedList<>();

        //FOR EACH FEATURE
        for (FeaturePOJO featurePOJO : featurePOJOS) {


            //FOR EACH SCENARIO IN FEATURE
            for (Elements element : featurePOJO.getElements()) {
                //SCENARIO PAYLOAD
                Map<String, Object> res = new HashMap<>();
                StashOutPojos stashOutPojos = new StashOutPojos();


                stashOutPojos.setFeatureIndex(1);
                stashOutPojos.setFeatureName(featurePOJO.getName());
                stashOutPojos.setFeatureURI(featurePOJO.getUri());
                stashOutPojos.setFeatureStatus(featurePOJO.getStatus().toUpperCase());
                int totalScenariosInFeature = featurePOJO.getFeaturesStatusesCount().values().stream().mapToInt(Integer::parseInt).sum();
                stashOutPojos.setTotalScenarios(totalScenariosInFeature);
                int totalPassedScenariosInFeature = featurePOJO.getFeaturesStatusesCount()
                        .entrySet().stream().filter(a -> a.getKey().equalsIgnoreCase("pass"))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values().stream().mapToInt(Integer::parseInt).sum();
                stashOutPojos.setTotalPassedScenarios(totalPassedScenariosInFeature);
                int totalFailedScenariosInFeature = featurePOJO.getFeaturesStatusesCount()
                        .entrySet().stream().filter(a -> a.getKey().equalsIgnoreCase("fail"))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values().stream().mapToInt(Integer::parseInt).sum();
                stashOutPojos.setTotalFailedScenarios(totalFailedScenariosInFeature);
                int totalSkippedScenariosInFeature = featurePOJO.getFeaturesStatusesCount()
                        .entrySet().stream().filter(a -> a.getKey().equalsIgnoreCase("skip"))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values().stream().mapToInt(Integer::parseInt).sum();
                stashOutPojos.setTotalSkippedScenarios(totalSkippedScenariosInFeature);
                int totalOtherStatusScenariosInFeature = featurePOJO.getFeaturesStatusesCount()
                        .entrySet().stream().filter(a -> a.getKey().equalsIgnoreCase("other"))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values().stream().mapToInt(Integer::parseInt).sum();
                stashOutPojos.setTotalOtherStatusScenarios(totalOtherStatusScenariosInFeature);


                stashOutPojos.setScenarioStartDate(getCurrDateTimeFromCukeStartTimestamp(element.getStart_timestamp(), "dd-MMM-yyyy"));
                stashOutPojos.setScenarioStartTime(getCurrDateTimeFromCukeStartTimestamp(element.getStart_timestamp(), "HH:mm:ssSSS"));


                stashOutPojos.setScenarioIndex(element.getId());
                stashOutPojos.setScenarioName(element.getName());
                stashOutPojos.setScenarioTags(element.getTags().stream().map(Tags::getTagName).collect(Collectors.toList()));
                stashOutPojos.setScenarioDurationInMillis(element.getDuration().toMillis());
                stashOutPojos.setScenarioStatus(element.getStatus().toUpperCase());
                String temp = element.getSteps().stream().map(Steps::getResult)
                        .map(Result::getErrorMessage).filter(Objects::nonNull).
                        filter(a -> !a.trim().isEmpty()).
                        collect(Collectors.joining());
                String scenarioError = temp.isEmpty() ? "NA" : temp;
                stashOutPojos.setScenarioError(scenarioError);

                for (Steps step : element.getSteps()) {
                    List<Embeddings> embeddings = step.getEmbeddings();
                    for (Embeddings embedding : embeddings) {
                       // embedding.
                    }
                }
                stashOutPojos.setScenarioLogsCount(new HashMap<>());

                int totalScenarioSteps = element.getScenariosStatusesCount().values().stream().mapToInt(Integer::parseInt).sum();
                stashOutPojos.setTotalSteps(totalScenarioSteps);
                int totalPassedScenarioSteps = element.getScenariosStatusesCount()
                        .entrySet().stream().filter(a -> a.getKey().equalsIgnoreCase("pass"))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values().stream().mapToInt(Integer::parseInt).sum();
                stashOutPojos.setTotalStepsPass(totalPassedScenarioSteps);
                int totalFailedScenarioSteps = element.getScenariosStatusesCount()
                        .entrySet().stream().filter(a -> a.getKey().equalsIgnoreCase("fail"))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values().stream().mapToInt(Integer::parseInt).sum();
                stashOutPojos.setTotalStepsPass(totalFailedScenarioSteps);
                int totalSkippedScenarioSteps = element.getScenariosStatusesCount()
                        .entrySet().stream().filter(a -> a.getKey().equalsIgnoreCase("skip"))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values().stream().mapToInt(Integer::parseInt).sum();
                stashOutPojos.setTotalStepsPass(totalSkippedScenarioSteps);
                int totalOtherStatusScenarioSteps = element.getScenariosStatusesCount()
                        .entrySet().stream().filter(a -> a.getKey().equalsIgnoreCase("other"))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values().stream().mapToInt(Integer::parseInt).sum();
                stashOutPojos.setTotalStepsPass(totalOtherStatusScenarioSteps);

                //Audit Data
                stashOutPojos.set_dataThrough(For.MAVEN_PLUGIN.name());
                stashOutPojos.set_loadTime(getCurrDateTimeInFormat("dd-MMM-yyy"));
                stashOutPojos.set_loadDate(getCurrDateTimeInFormat("HH:mm:ss.SSS"));
                String machineName = "UNKNOWN";
                try {
                    machineName = InetAddress.getLocalHost().getHostName();
                } catch (Exception ignored) {
                }
                stashOutPojos.set_machineName(machineName);
                stashOutPojos.set_userName(System.getProperty("user.name").toUpperCase(Locale.ROOT));

                if (element.isScenario()) {
                    results.add(stashOutPojos);
                }

            }//End of Elements Loop
        }//End of Feature loop
        return results;
    }


    private static String getCurrDateTimeFromCukeStartTimestamp(String cukeStartTS, String dtformats) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        LocalDateTime date1 = LocalDateTime.parse(cukeStartTS, formatter);
        Instant instant = date1.toInstant(ZoneOffset.UTC);
        DateTimeFormatter a = DateTimeFormatter.ofPattern(dtformats).withZone(ZoneOffset.UTC);
        return a.format(instant);
    }

    private static String getCurrDateTimeInFormat(String dtformats) {
        Date toPrint = new Date();//"yyyy-MM-dd HH:mm:ss.SSS z"
        SimpleDateFormat format = new SimpleDateFormat(dtformats);
        format.setTimeZone(TimeZone.getDefault());
        return (format.format(toPrint));
    }


}
