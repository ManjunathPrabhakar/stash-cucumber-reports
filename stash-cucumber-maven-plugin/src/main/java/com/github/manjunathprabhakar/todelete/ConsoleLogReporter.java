package com.github.manjunathprabhakar.todelete;

import com.github.manjunathprabhakar.moved.pojos.inparser.Elements;
import com.github.manjunathprabhakar.moved.pojos.inparser.FeaturePOJO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConsoleLogReporter {

    public void generateConsoleLog(Map<String, Object> params) throws Exception {
        //CODE GOES DOWN BELOW
        List<FeaturePOJO> jsons = ReportHandler.getJSONsToList(params.get("jsonPath").toString());
        List<FeaturePOJO> featurePOJOS = ReportHandler.combineMultipleScenariosOfSameFeature(jsons);

        System.out.println("----------------------------------------------------------");
        System.out.println("------------------- Features Overview --------------------");
        System.out.println("----------------------------------------------------------");
        System.out.println("Total Features      : " + params.get("totalFeatures").toString());
        System.out.println("Passed Features     : " + params.get("totalPassFeatures").toString());
        System.out.println("Failed Features     : " + params.get("totalFailFeatures").toString());
        System.out.println("Skipped Features    : " + params.get("totalSkipFeatures").toString());
        System.out.println("Other Features      : " + params.get("totalOtherFeatures").toString());
        System.out.println("Feature Pass %      : " + params.get("featurePassPercentage").toString() + "%");
        System.out.println("Overall Exec Time   : " + params.get("totalExecutionTime").toString());
        System.out.println("----------------------------------------------------------");
        System.out.println("------------------- Scenarios Overview -------------------");
        System.out.println("----------------------------------------------------------");
        System.out.println("Total Scenarios     : " + params.get("totalScenarios").toString());
        System.out.println("Passed Scenarios    : " + params.get("totalPassScenarios").toString());
        System.out.println("Failed Scenarios    : " + params.get("totalFailScenarios").toString());
        System.out.println("Skipped Scenarios   : " + params.get("totalSkipScenarios").toString());
        System.out.println("Other Scenarios     : " + params.get("totalOtherScenarios").toString());
        System.out.println("Scenarios Pass %    : " + params.get("scenariosPassPercentage").toString() + "%");
        System.out.println("----------------------------------------------------------");
        System.out.println("------------------- Steps Overview -----------------------");
        System.out.println("----------------------------------------------------------");
        System.out.println("Total Steps     : " + params.get("totalSteps").toString());
        System.out.println("Passed Steps    : " + params.get("totalPassSteps").toString());
        System.out.println("Failed Steps    : " + params.get("totalFailSteps").toString());
        System.out.println("Skipped Steps   : " + params.get("totalSkipSteps").toString());
        System.out.println("Other Steps     : " + params.get("totalOtherSteps").toString());
        System.out.println("Steps Pass %    : " + params.get("StepsPassPercentage").toString() + "%");
        System.out.println("----------------------------------------------------------");
        System.out.println("------------------- Steps Overview -----------------------");
        System.out.println("----------------------------------------------------------");
        System.out.println("Total Tags     : " + params.get("totalTags").toString());
        System.out.println("----------------------------------------------------------");

        if (params.get("featurePassPercentage").toString().equalsIgnoreCase("100")) {
            return;
        }

        System.out.println("-------------- Features/Scenarios Broken! ----------------");
        int featureCount = 1;
        int scncount = 1;
        for (FeaturePOJO featurePOJO : featurePOJOS) {
            List<String> scenario = featurePOJO.getElements().stream().filter(s -> s.getType().equalsIgnoreCase("scenario") && !s.getStatus().equalsIgnoreCase("pass")).map(Elements::getName).collect(Collectors.toList());
            if (scenario.size() > 0) {
                System.out.println("----------------------------------------------------------");
                System.out.println("------------------------ Feature -------------------------");
                System.out.println("F" + featureCount++ + ". " + featurePOJO.getName());
                System.out.println("----------------------- Scenarios ------------------------");
                for (String a : scenario) {
                    System.out.println("S" + scncount++ + ". " + a);
                }
            }
            scncount = 1;
        }
        System.out.println("----------------------------------------------------------");
    }

    private List<String> gatherDataInList(List<List<String>> data) {

        List<String> res = new ArrayList<>();

        int maxFeatureLength = 0, maxTotalScenariosLength = 0, maxTotPassLen = 0, maxTotFailLen = 0, maxTotSkipLen = 0, maxPassPer = 0;

        for (List<String> s : data) {
            int len = 0;
            len = s.get(0).length();
            if (len > maxFeatureLength) {
                maxFeatureLength = len;
            }
            len = s.get(1).length();
            if (len > maxTotalScenariosLength) {
                maxTotalScenariosLength = len;
            }
            len = s.get(2).length();
            if (len > maxTotPassLen) {
                maxTotPassLen = len;
            }
            len = s.get(3).length();
            if (len > maxTotFailLen) {
                maxTotFailLen = len;
            }
            len = s.get(4).length();
            if (len > maxTotSkipLen) {
                maxTotSkipLen = len;
            }
            len = s.get(5).length();
            if (len > maxPassPer) {
                maxPassPer = len;
            }

        }

        maxFeatureLength = maxFeatureLength + 3;
        maxTotalScenariosLength = maxTotalScenariosLength + 3;
        maxTotPassLen = maxTotPassLen + 3;
        maxTotFailLen = maxTotFailLen + 3;
        maxTotSkipLen = maxTotSkipLen + 3;
        maxPassPer = maxPassPer + 3;

        boolean line = false;
        int dataCount = 0;
        int dataLimit = data.size() - 1;
        List<String> dataToLog = new ArrayList<>();
        for (List<String> ls : data) {
            String format = String.format("|%-" + maxFeatureLength + "s|%-" + (maxTotalScenariosLength)
                            + "s|%-" + maxTotPassLen + "s|%-" + maxTotFailLen + "s|%-" + maxTotSkipLen + "s|%-" + maxPassPer + "s|",
                    " " + ls.get(0), " " + ls.get(1), " " + ls.get(2), " " + ls.get(3), " " + ls.get(4), " " + ls.get(5));

            dataToLog.add(format);
        }

        String lines = "";

        int formatLen = dataToLog.get(0).length();
        for (int i = 0; i < formatLen; i++) {
            lines = lines + "-";
        }

        res.add(lines); //Line
        res.add(dataToLog.get(0)); //Header
        res.add(lines); //Line

        for (int i = 1; i < dataToLog.size() - 1; i++) {
            res.add(dataToLog.get(i));  //Rows
        }
        res.add(lines); //Line
        res.add(dataToLog.get(dataToLog.size() - 1)); //Footer
        res.add(lines); //Line


        return res;
    }

}
