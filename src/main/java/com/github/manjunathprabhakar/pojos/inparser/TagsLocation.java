package com.github.manjunathprabhakar.pojos.inparser;

import com.google.gson.annotations.SerializedName;

/**
 * @author Manjunath Prabhakar (Manjunath-PC)
 * @created 19/09/2020
 * @project cooker-cucumber-reporter
 */
public class TagsLocation {

    @SerializedName(value = "line")
    private int line = 0;

    @SerializedName(value = "column")
    private int column = 0;

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
