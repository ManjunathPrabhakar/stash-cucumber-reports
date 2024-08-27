package com.github.manjunathprabhakar.pojos.inparser;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Manjunath Prabhakar (Manjunath-PC)
 * @created 19/09/2020
 * @project cooker-cucumber-reporter
 *
 * <p>Used to get the values passed via DataTable</p>
 */
public class DataTableRows {

    @SerializedName(value = "cells")
    private List<String> cells = new ArrayList<String>();


    public List<String> getCells() {
        return cells;
    }

    public void setCells(List<String> cells) {
        this.cells = cells;
    }
}
