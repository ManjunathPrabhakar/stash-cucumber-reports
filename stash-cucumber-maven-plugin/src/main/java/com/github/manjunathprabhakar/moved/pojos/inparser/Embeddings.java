package com.github.manjunathprabhakar.moved.pojos.inparser;

import com.google.gson.annotations.SerializedName;

import java.util.Base64;

/**
 * @author Manjunath Prabhakar (Manjunath-PC)
 * @created 19/09/2020
 * @project cooker-cucumber-reporter
 *
 * <p>Used to get Embedded data for a Step</p>
 */
public class Embeddings {

    @SerializedName(value = "data")
    private String data;

    @SerializedName(value = "mime_type")
    private String mime_type;

    @SerializedName(value = "name")
    private String name = "";

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMime_type() {
        return mime_type;
    }

    public void setMime_type(String mime_type) {
        this.mime_type = mime_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDecodedData(String base64Encoded) {
        return new String(Base64.getDecoder().decode(base64Encoded));
    }
}
