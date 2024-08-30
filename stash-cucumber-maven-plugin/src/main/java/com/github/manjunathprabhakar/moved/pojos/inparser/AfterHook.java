package com.github.manjunathprabhakar.moved.pojos.inparser;

import com.google.gson.annotations.SerializedName;

import java.time.Duration;

/**
 * @author Manjunath Prabhakar (Manjunath-PC)
 * @created 19/09/2020
 * @project cooker-cucumber-reporter
 *
 * <p>Read all tags within "after" tag</p>
 * <p>It Might Exist for Scenario(s), Step(s) Only</p>
 */
public class AfterHook {

    @SerializedName(value = "result")
    private Result result;

    @SerializedName(value = "match")
    private Match match;

    public Duration getDuration() {
        return Duration.ofNanos(result.getDuration());
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }


}
