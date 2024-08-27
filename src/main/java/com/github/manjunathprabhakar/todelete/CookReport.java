package com.github.manjunathprabhakar.todelete;

import com.github.manjunathprabhakar.pojos.inparser.FeaturePOJO;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Manjunath Prabhakar (Manjunath-PC)
 * @created 23/09/2020
 * @project cooker-maven-plugin
 */
public class CookReport {
    Map<String, Object> params = new HashMap<>();
    Map<String, Object> Allparams = new HashMap<>();

    public CookReport(Map<String, Object> params) {
        this.params = params;
    }


    public void showLogReport() throws Exception {
        new ConsoleLogReporter().generateConsoleLog(this.Allparams);
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> params = new HashMap<>();
        //Strings
        params.put("jsonPath", "C:\\Users\\MANJU\\intellijprojects\\stash-cucumber-reports\\json");



        Map<String, Object> input = new HashMap<String, Object>(params);

        List<FeaturePOJO> jsons = ReportHandler.getJSONsToList(params.get("jsonPath").toString());

        List<FeaturePOJO> featurePOJOS = ReportHandler.combineMultipleScenariosOfSameFeature(jsons);

        Map<String, Object> featureData = ReportHandler.getFeatureDatas(featurePOJOS);
        input.putAll(featureData);

        Map<String, Object> scenarioData = ReportHandler.getScenariosData(featurePOJOS);
        input.putAll(scenarioData);


        Map<String, Object> scenarioData1 = ReportHandler.getStepsData(featurePOJOS);
        input.putAll(scenarioData1);

        Map<String, Object> scenarioData11 = ReportHandler.getScenariosTagData(featurePOJOS);
        input.putAll(scenarioData11);

        input.put("totalExecutionTime", ReportHandler.getDurationStringFormat((Duration) input.get("totalDuration")));
        input.put("featurepojos", featurePOJOS);

        input.put("cookerReportTitle", "COOKER_PROJECT_NAME");

        new ConsoleLogReporter().generateConsoleLog(input);

    }

}
