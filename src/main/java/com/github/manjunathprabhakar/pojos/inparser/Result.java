package com.github.manjunathprabhakar.pojos.inparser;

import com.google.gson.annotations.SerializedName;

/**
 * @author Manjunath Prabhakar (Manjunath-PC)
 * @created 19/09/2020
 * @project cooker-cucumber-reporter
 *
 * <p>Holds Result Object</p>
 */
public class Result {

    @SerializedName(value = "duration")
    private long duration = 0;

    @SerializedName(value = "status")
    private String status = "";

    @SerializedName(value = "error_message")
    private String errorMessage = "";

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
