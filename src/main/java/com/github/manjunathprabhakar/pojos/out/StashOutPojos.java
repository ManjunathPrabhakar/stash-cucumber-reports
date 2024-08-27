package com.github.manjunathprabhakar.pojos.out;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StashOutPojos {

    private long featureIndex;
    private String featureName;
    private String featureStatus;
    private long totalScenarios;
    private long totalPassedScenarios;
    private long totalFailedScenarios;
    private long totalSkippedScenarios;
    private long totalOtherStatusScenarios;
    private String featureURI;
    private long scenarioIndex;
    private String scenarioName;
    private String scenarioStatus;
    private long totalSteps;
    private long totalStepsPass;
    private long totalStepsFail;
    private long totalStepsSkip;
    private long totalStepsOtherStatus;
    private List<String> scenarioTags;
    private String scenarioError;
    private long scenarioDuration;
    //Audit
    private String timeStamp;
    private String machineName;
    private String userName;

}
