package com.github.manjunathprabhakar.core.pojos.inparser;

import com.google.gson.annotations.SerializedName;

public class DocStrings {

    @SerializedName(value = "content_type")
    private String content_type = "";

    @SerializedName(value = "line")
    private String line;

    @SerializedName(value = "value")
    private String value;

    public String getContent_type() {
        return content_type;
    }

    public String getLine() {
        return line;
    }

    public String getValue() {
        return value;
    }
}
