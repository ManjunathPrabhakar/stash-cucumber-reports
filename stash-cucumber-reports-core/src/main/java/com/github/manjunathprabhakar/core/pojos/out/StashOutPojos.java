package com.github.manjunathprabhakar.core.pojos.out;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    private long scenarioIndex;
    private String scenarioName;
    private List<String> scenarioTags;
    private String scenarioStatus;
    private String scenarioError;
    private String scenarioStartDate;
    private String scenarioStartTime;
    private long scenarioDuration;
    private long totalSteps;
    private long totalStepsPass;
    private long totalStepsFail;
    private long totalStepsSkip;
    private long totalStepsOtherStatus;

    //Audit
    private String dataThrough;
    private String timeStamp;
    private String machineName;
    private String userName;

}
