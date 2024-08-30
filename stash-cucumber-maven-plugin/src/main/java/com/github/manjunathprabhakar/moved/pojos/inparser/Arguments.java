package com.github.manjunathprabhakar.moved.pojos.inparser;

import com.google.gson.annotations.SerializedName;

/**
 * @author Manjunath Prabhakar (Manjunath-PC)
 * @created 19/09/2020
 * @project cooker-cucumber-reporter
 *
 * <p>This Reads Arguments/Parameters Value of a Step</p>
 * <p>This Might Exist under a Step</p>
 */
public class Arguments {

    @SerializedName(value = "val")
    private String val = "";

    @SerializedName(value = "offset")
    private int offset = 0;

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
