package com.github.manjunathprabhakar;

import lombok.Builder;
import lombok.Getter;

import java.net.URI;

@Builder
@Getter
public class RunTimeScenarioStatuses {
    private URI featureURI;
    private String featureName;
    private String scenarioName;
    private String scenarioStatus;
}
