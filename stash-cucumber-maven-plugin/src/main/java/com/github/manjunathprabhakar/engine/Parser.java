package com.github.manjunathprabhakar.engine;

import com.github.manjunathprabhakar.moved.constants.DataFields;
import com.github.manjunathprabhakar.moved.pojos.inparser.*;
import com.github.manjunathprabhakar.moved.pojos.out.StashOutPojos;
import com.github.manjunathprabhakar.todelete.ReportHandler;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Parser {
    public static List<StashOutPojos> parse(String jsonpath) throws Exception {
        //NEEDED DATA
        List<DataFields> featureIndexsdata = Arrays.asList(DataFields.values());

        //TEST DATA JSONS
        List<FeaturePOJO> jsons = ReportHandler.getJSONsToList(jsonpath);
        List<FeaturePOJO> featurePOJOS = ReportHandler.combineMultipleScenariosOfSameFeature(jsons);

        //PAYLOAD
        List<Map<String, Object>> jsonData = new LinkedList<>();
        List<StashOutPojos> results = new LinkedList<>();

        //INDEX INCREMENT
        AtomicInteger featureIndex = new AtomicInteger(0);

        //FOR EACH FEATURE
        for (FeaturePOJO featurePOJO : featurePOJOS) {

            //SCENARIO INDEX INCREMENT
            AtomicInteger scenarioIndex = new AtomicInteger(0);


            //FOR EACH SCENARIO IN FEATURE
            for (Elements element : featurePOJO.getElements()) {
                //SCENARIO PAYLOAD
                Map<String, Object> res = new HashMap<>();
                StashOutPojos stashOutPojos = new StashOutPojos();


                if (featureIndexsdata.contains(DataFields.FEATURE_INDEX)) {
                    long v = featureIndex.incrementAndGet();
                    stashOutPojos.setFeatureIndex(v);
                    res.put("featureIndex", v);
                }
                if (featureIndexsdata.contains(DataFields.FEATURE_NAME)) {
                    String fName = featurePOJO.getName();
                    stashOutPojos.setFeatureName(fName);
                    res.put("featureName", fName);
                }
                if (featureIndexsdata.contains(DataFields.FEATURE_STATUS)) {
                    String fStatus = featurePOJO.getStatus();
                    stashOutPojos.setFeatureStatus(fStatus);
                    res.put("featureStatus", fStatus);
                }
                if (featureIndexsdata.contains(DataFields.TOTAL_SCENARIOS)) {
                    int ftotScens = featurePOJO.getFeaturesStatusesCount().values().stream().mapToInt(Integer::parseInt).sum();
                    stashOutPojos.setTotalScenarios(ftotScens);
                    res.put("totalScenarios", ftotScens);
                }
                if (featureIndexsdata.contains(DataFields.TOTAL_PASSED_SCENARIOS)) {
                    int fPassScens = featurePOJO.getFeaturesStatusesCount()
                            .entrySet().stream().filter(a -> a.getKey().equalsIgnoreCase("pass"))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values().stream().mapToInt(Integer::parseInt).sum();
                    stashOutPojos.setTotalPassedScenarios(fPassScens);
                    res.put("totalPassedScenarios",
                            fPassScens);
                }
                if (featureIndexsdata.contains(DataFields.TOTAL_FAILED_SCENARIOS)) {
                    int fFailScens = featurePOJO.getFeaturesStatusesCount()
                            .entrySet().stream().filter(a -> a.getKey().equalsIgnoreCase("fail"))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values().stream().mapToInt(Integer::parseInt).sum();
                    stashOutPojos.setTotalFailedScenarios(fFailScens);
                    res.put("totalFailedScenarios", fFailScens);
                }
                if (featureIndexsdata.contains(DataFields.TOTAL_SKIPPED_SCENARIOS)) {
                    int fSkipScens = featurePOJO.getFeaturesStatusesCount()
                            .entrySet().stream().filter(a -> a.getKey().equalsIgnoreCase("skip"))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values().stream().mapToInt(Integer::parseInt).sum();
                    stashOutPojos.setTotalSkippedScenarios(fSkipScens);
                    res.put("totalSkippedScenarios", fSkipScens);
                }
                if (featureIndexsdata.contains(DataFields.TOTAL_OTHERSTATUS_SCENARIOS)) {
                    int fOtherScens = featurePOJO.getFeaturesStatusesCount()
                            .entrySet().stream().filter(a -> a.getKey().equalsIgnoreCase("other"))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values().stream().mapToInt(Integer::parseInt).sum();
                    stashOutPojos.setTotalOtherStatusScenarios(fOtherScens);
                    res.put("totalOtherStatusScenarios", fOtherScens);
                }

                String featureURI = featurePOJO.getUri();
                stashOutPojos.setFeatureURI(featureURI);
                res.put("featureURI", featureURI);


                if (featureIndexsdata.contains(DataFields.TIME_STAMP)) {
                    String ts = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a").format(new Date());
                    res.put("timeStamp", ts);
                    stashOutPojos.setTimeStamp(ts);
                }
                if (featureIndexsdata.contains(DataFields.MACHINE_NAME)) {
                    String hostname = "unknown";
                    try {
                        InetAddress addr = InetAddress.getLocalHost();
                        hostname = addr.getHostName();
                    } catch (UnknownHostException ex) {
                        System.out.println("Hostname can not be resolved");
                    }
                    res.put("machineName", hostname);
                    stashOutPojos.setMachineName(hostname);
                }
                if (featureIndexsdata.contains(DataFields.USER_NAME)) {
                    String uname = System.getProperty("user.name");
                    res.put("userName", uname);
                    stashOutPojos.setUserName(uname);
                }

                //////////////////////////////////////////////////
                //if (element.isScenario()) {

                if (featureIndexsdata.contains(DataFields.SCENARIO_INDEX)) {
                    int sIndex = scenarioIndex.incrementAndGet();
                    stashOutPojos.setScenarioIndex(sIndex);
                    res.put("scenarioIndex", sIndex);
                }
                if (featureIndexsdata.contains(DataFields.SCENARIO_NAME)) {
                    String sName = element.getName();
                    stashOutPojos.setScenarioName(sName);
                    res.put("scenarioName", sName);
                }
                if (featureIndexsdata.contains(DataFields.SCENARIO_STATUS)) {
                    String sStatus = element.getStatus();
                    stashOutPojos.setScenarioStatus(sStatus);
                    res.put("scenarioStatus", sStatus);
                }

                if (featureIndexsdata.contains(DataFields.TOTAL_STEPS)) {
                    int sTotSteps = element.getScenariosStatusesCount().values().stream().mapToInt(Integer::parseInt).sum();
                    stashOutPojos.setTotalSteps(sTotSteps);
                    res.put("totalSteps", sTotSteps);
                }
                if (featureIndexsdata.contains(DataFields.TOTAL_PASSED_STEPS)) {
                    int sStepPass = element.getScenariosStatusesCount()
                            .entrySet().stream().filter(a -> a.getKey().equalsIgnoreCase("pass"))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values().stream().mapToInt(Integer::parseInt).sum();
                    res.put("totalStepsPass", sStepPass);
                    stashOutPojos.setTotalStepsPass(sStepPass);
                }
                if (featureIndexsdata.contains(DataFields.TOTAL_FAILED_STEPS)) {
                    int sStepFail = element.getScenariosStatusesCount()
                            .entrySet().stream().filter(a -> a.getKey().equalsIgnoreCase("fail"))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values().stream().mapToInt(Integer::parseInt).sum();
                    res.put("totalStepsFail", sStepFail);
                    stashOutPojos.setTotalStepsFail(sStepFail);
                }
                if (featureIndexsdata.contains(DataFields.TOTAL_SKIPPED_STEPS)) {
                    int sStepSkip = element.getScenariosStatusesCount()
                            .entrySet().stream().filter(a -> a.getKey().equalsIgnoreCase("skip"))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values().stream().mapToInt(Integer::parseInt).sum();
                    res.put("totalStepsSkip", sStepSkip);
                    stashOutPojos.setTotalStepsSkip(sStepSkip);
                }
                if (featureIndexsdata.contains(DataFields.TOTAL_OTHERSTATUS_STEPS)) {
                    int sStepOther = element.getScenariosStatusesCount()
                            .entrySet().stream().filter(a -> a.getKey().equalsIgnoreCase("other"))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values().stream().mapToInt(Integer::parseInt).sum();
                    res.put("totalStepsOtherStatus", sStepOther);
                    stashOutPojos.setTotalStepsOtherStatus(sStepOther);
                }

                List<String> collect1 = element.getTags().stream().map(Tags::getTagName).collect(Collectors.toList());
                res.put("scenarioTags", collect1);
                stashOutPojos.setScenarioTags(collect1);


                String collect = element.getSteps().stream().map(Steps::getResult)
                        .map(Result::getErrorMessage).filter(Objects::nonNull).
                        filter(a -> !a.trim().isEmpty()).
                        collect(Collectors.joining());
                // collect = collect.contains("\n") ? collect.substring(0, collect.indexOf("\n")) : collect;
                String errors = collect.isEmpty() ? "NA" : collect;
                res.put("scenarioError", errors);
                stashOutPojos.setScenarioError(errors);

                //res.put("scenarioDuration1", element.getDuration());
                stashOutPojos.setScenarioDuration(element.getDuration().toMillis());


                if (element.isScenario()) {
                    jsonData.add(res);
                    results.add(stashOutPojos);
                }


            }//END OF ELEMENT POJO FOR LOOP
            featureIndex = new AtomicInteger(0);

        }//END OF FEATURE POJO FOR LOOP
        return results;


    }

    public static String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
                "%d:%02d:%02d",
                absSeconds / 3600,
                (absSeconds % 3600) / 60,
                absSeconds % 60);
        return seconds < 0 ? "-" + positive : positive;
    }
}