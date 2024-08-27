package com.github.manjunathprabhakar.pojos.inparser;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Manjunath Prabhakar (Manjunath-PC)
 * @created 19/09/2020
 * @project cooker-cucumber-reporter
 *
 * <p>Holds Location & DataTable Arguments of a Step</p>
 */
public class Match {

    @SerializedName(value = "location")
    private String location = "";

    @SerializedName(value = "arguments")
    private List<Arguments> arguments = new ArrayList<Arguments>();

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Arguments> getArguments() {
        return arguments;
    }

    public void setArguments(List<Arguments> arguments) {
        this.arguments = arguments;
    }
}
