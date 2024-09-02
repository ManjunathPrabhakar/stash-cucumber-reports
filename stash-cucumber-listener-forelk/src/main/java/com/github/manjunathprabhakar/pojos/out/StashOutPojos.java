package com.github.manjunathprabhakar.pojos.out;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class StashOutPojos {

    private long featureIndex;
    private String featureName;
    private String featureURI;
    private String featureStatus;
    private long totalScenarios;
    private long totalPassedScenarios;
    private long totalFailedScenarios;
    private long totalSkippedScenarios;
    private long totalOtherStatusScenarios;

    private String scenarioIndex;
    private String scenarioName;
    private List<String> scenarioTags;
    private String scenarioStartDate;
    private String scenarioStartTime;
    private String scenarioStatus;
    private String scenarioError;
    private long scenarioDurationInNanos;
    private long totalSteps;
    private long totalStepsPass;
    private long totalStepsFail;
    private long totalStepsSkip;
    private long totalStepsOtherStatus;
    private Map<String,Integer> scenarioLogsCount;
    //Audit
    private String _dataThrough;
    private String _loadDate;
    private String _loadTime;
    private String _machineName;
    private String _userName;

}
