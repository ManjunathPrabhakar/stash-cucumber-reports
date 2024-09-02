package com.github.manjunathprabhakar.core.engine;

import com.github.manjunathprabhakar.core.common.fileFactory.FileUtility;
import com.github.manjunathprabhakar.core.pojos.inparser.Elements;
import com.github.manjunathprabhakar.core.pojos.inparser.FeaturePOJO;
import com.github.manjunathprabhakar.core.pojos.inparser.Steps;
import com.github.manjunathprabhakar.core.pojos.inparser.Tags;
import com.google.gson.Gson;

import java.io.File;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class Handler {

    public static List<FeaturePOJO> getJSONsToList(String jsonspath) throws Exception {
        List<FeaturePOJO> features = new ArrayList<FeaturePOJO>();
        Gson gson = new Gson();

        FileUtility featurecontent = new FileUtility(jsonspath);
        //Get all *.feature files from existing feature files directory
        List<File> featurefiles = featurecontent.getFiles(".json");

        if (featurefiles.size() > 0) {
            for (File jsonFile : featurefiles) {
                String str = FileUtility.readAndGetFileContent(jsonFile.getPath());
                FeaturePOJO[] featurePOJOS = gson.fromJson(str, FeaturePOJO[].class);
                features.addAll(Arrays.asList(featurePOJOS));
            }
        }

        return features;
    }

    public static List<FeaturePOJO> combineMultipleScenariosOfSameFeature(List<FeaturePOJO> featuresList) throws Exception {
        List<FeaturePOJO> finalFeatures = new ArrayList<FeaturePOJO>();

        //Combine Different Scenarios of Same Feature name Jsons as One
        Map<String, FeaturePOJO> featuresMap = new HashMap<>();
        for (FeaturePOJO feature : featuresList) {
            //If Map doesn't have the Feature Name (Key)
            if (featuresMap.get(feature.getName()) == null)
                //Add key as Feature Name & Value as The Feature Itself
                featuresMap.put(feature.getName(), feature);
            else
                //Get the Map with Feature Name (Key),
                // Get Its feature's elements and add the elements of same feature file
                featuresMap.get(feature.getName()).getElements().addAll(feature.getElements());
        }

        finalFeatures = new ArrayList<>(featuresMap.values());

        return finalFeatures;
    }

    public static Map<String, Object> getStepsData(List<FeaturePOJO> featurePOJOS) {
        Map<String, Object> res = new HashMap<String, Object>();
        Map<String, Integer> check = new LinkedHashMap<>();
        long totalscenarios = 0, scenariospasscount = 0, scenariosfailcount = 0, scenariosskipcount = 0, scenariosothercount = 0;
        for (FeaturePOJO featureList : featurePOJOS) {
            for (Elements element : featureList.getElements()) {
                if (element.isScenario()) {
                    for (Steps step : element.getSteps()) {
                        if (step.getResult().getStatus().equalsIgnoreCase("passed"))
                            scenariospasscount = scenariospasscount + 1;
                        if (step.getResult().getStatus().equalsIgnoreCase("failed"))
                            scenariosfailcount = scenariosfailcount + 1;
                        if (step.getResult().getStatus().equalsIgnoreCase("skipped"))
                            scenariosskipcount = scenariosskipcount + 1;

                        if (check.containsKey(step.getName())) {
                            int count = check.get(step.getName());
                            check.put(step.getName(), count = count + 1);
                        } else {
                            check.put(step.getName(), 1);
                        }
                    }
                }

            }
        }

        List<String> keys = check.keySet().stream().sorted().collect(Collectors.toList());

        for (String key : keys) {
            int value = check.get(key);
            System.out.println(key + " => " + value);
        }
        totalscenarios = scenariosfailcount + scenariosothercount + scenariosskipcount + scenariospasscount;
        res.put("totalSteps", "" + totalscenarios);
        res.put("totalPassSteps", "" + scenariospasscount);
        res.put("totalFailSteps", "" + scenariosfailcount);
        res.put("totalSkipSteps", "" + scenariosskipcount);
        res.put("totalOtherSteps", "" + scenariosothercount);
        res.put("StepsPassPercentage", (int) Math.round(((float) scenariospasscount / totalscenarios) * 100));
        return res;
    }

    public static Map<String, Object> getScenariosTagData(List<FeaturePOJO> featurePOJOS) {
        Map<String, Object> res = new HashMap<String, Object>();
        Map<String, Integer> tagsCount = new LinkedHashMap<>();
        long totalscenarios = 0, scenariospasscount = 0, scenariosfailcount = 0, scenariosskipcount = 0, scenariosothercount = 0;
        for (FeaturePOJO featureList : featurePOJOS) {
            List<Tags> tags = featureList.getTags();
            for (Tags tag : tags) {

                if (tagsCount.containsKey(tag.getTagName())) {
                    int count = tagsCount.get(tag.getTagName());
                    tagsCount.put(tag.getTagName(), count = count + 1);
                } else {
                    tagsCount.put(tag.getTagName(), 1);
                }

            }
            for (Elements element : featureList.getElements()) {
                List<Tags> stags = element.getTags();
                for (Tags tag : stags) {

                    if (tagsCount.containsKey(tag.getTagName())) {
                        int count = tagsCount.get(tag.getTagName());
                        tagsCount.put(tag.getTagName(), count = count + 1);
                    } else {
                        tagsCount.put(tag.getTagName(), 1);
                    }

                }

            }

        }
        List<String> keys = tagsCount.keySet().stream().sorted().collect(Collectors.toList());

        for (String key : keys) {
            int value = tagsCount.get(key);
            System.out.println(key + " => " + value);
        }

        res.put("totalTags", "" + tagsCount.size());
//        res.put("totalPassScenarios", "" + scenariospasscount);
//        res.put("totalFailScenarios", "" + scenariosfailcount);
//        res.put("totalSkipScenarios", "" + scenariosskipcount);
//        res.put("totalOtherScenarios", "" + scenariosothercount);
//        res.put("scenariosPassPercentage", (int) Math.round(((float) scenariospasscount / totalscenarios) * 100));
        return res;
    }

    public static Map<String, Object> getScenariosData(List<FeaturePOJO> featurePOJOS) {
        Map<String, Object> res = new HashMap<String, Object>();
        long totalscenarios = 0, scenariospasscount = 0, scenariosfailcount = 0, scenariosskipcount = 0, scenariosothercount = 0;
        for (FeaturePOJO featureList : featurePOJOS) {
            totalscenarios = totalscenarios +
                    featureList.getElements().stream().filter(e -> e.getType().equalsIgnoreCase("scenario")).count();
            scenariospasscount = scenariospasscount +
                    featureList.getElements().stream().filter(e -> e.getType().equalsIgnoreCase("scenario") & e.getStatus().equalsIgnoreCase("pass")).count();
            scenariosfailcount = scenariosfailcount +
                    featureList.getElements().stream().filter(e -> e.getType().equalsIgnoreCase("scenario") & e.getStatus().equalsIgnoreCase("fail")).count();
            scenariosskipcount = scenariosskipcount +
                    featureList.getElements().stream().filter(e -> e.getType().equalsIgnoreCase("scenario") & e.getStatus().equalsIgnoreCase("skip")).count();
            scenariosothercount = scenariosothercount +
                    featureList.getElements().stream().filter(e -> e.getType().equalsIgnoreCase("scenario") & e.getStatus().equalsIgnoreCase("other")).count();
        }

        res.put("totalScenarios", "" + totalscenarios);
        res.put("totalPassScenarios", "" + scenariospasscount);
        res.put("totalFailScenarios", "" + scenariosfailcount);
        res.put("totalSkipScenarios", "" + scenariosskipcount);
        res.put("totalOtherScenarios", "" + scenariosothercount);
        res.put("scenariosPassPercentage", (int) Math.round(((float) scenariospasscount / totalscenarios) * 100));
        return res;
    }

    public static Map<String, Object> getFeatureDatas(List<FeaturePOJO> featurePOJOS) {

        Map<String, Object> res = new HashMap<String, Object>();
        long totalFeatures = featurePOJOS.size();
        long totalPassFeatures = featurePOJOS.stream().filter(s -> s.getStatus().equalsIgnoreCase("pass")).count();
        long totalFailFeatures = featurePOJOS.stream().filter(s -> s.getStatus().equalsIgnoreCase("fail")).count();
        long totalSkipFeatures = featurePOJOS.stream().filter(s -> s.getStatus().equalsIgnoreCase("skip")).count();
        long totalOtherFeatures = featurePOJOS.stream().filter(s -> s.getStatus().equalsIgnoreCase("other")).count();
        Duration totalExectionDuration = Duration.ofSeconds(0);
        for (FeaturePOJO f : featurePOJOS) {
            Duration featureDur = f.getDuration();
            totalExectionDuration = totalExectionDuration.plus(featureDur);
        }
        res.put("totalFeatures", "" + totalFeatures);
        res.put("totalPassFeatures", "" + totalPassFeatures);
        res.put("totalFailFeatures", "" + totalFailFeatures);
        res.put("totalSkipFeatures", "" + totalSkipFeatures);
        res.put("totalOtherFeatures", "" + totalOtherFeatures);
        res.put("featurePassPercentage", (int) Math.round(((float) totalPassFeatures / totalFeatures) * 100));
        res.put("totalDuration", totalExectionDuration);
        return res;
    }

    public static String getDurationStringFormat(Duration duration) {

        long days = duration.toDays();
        duration = duration.minusDays(days);
        long hours = duration.toHours();
        duration = duration.minusHours(hours);
        long mins = duration.toMinutes();
        duration = duration.minusMinutes(mins);
        long secs = duration.getSeconds();
        duration = duration.minusSeconds(secs);
        long mils = duration.toMillis();

        String res = ((days > 0 ? (days + "d") : "") + " " +
                (hours > 0 ? (String.format("%02d", hours) + "h") : "") + " " +
                (mins > 0 ? (String.format("%02d", mins) + "m") : "") + " " +
                (secs > 0 ? (String.format("%02d", secs) + "s") : "") + " " +
                (mils > 0 ? (String.format("%04d", mils).substring(0, 3) + "ms") : "")).trim();

        return res.isEmpty() ? "0ms" : res;
    }
}
