package com.github.manjunathprabhakar.pojos.inparser;

import com.google.gson.annotations.SerializedName;

/**
 * @author Manjunath Prabhakar (Manjunath-PC)
 * @created 19/09/2020
 * @project cooker-cucumber-reporter
 *
 * <p>Get Tags Data from JSON</p>
 */
public class Tags {

    @SerializedName(value = "name")
    private String tagName;

    @SerializedName(value = "tag")
    private String tagType;

    @SerializedName(value = "location") //Probably Only for Feature Level Tags
    private TagsLocation tagsLocation = new TagsLocation();

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public TagsLocation getTagsLocationPOJO() {
        return tagsLocation;
    }

    public void setTagsLocationPOJO(TagsLocation tagsLocation) {
        this.tagsLocation = tagsLocation;
    }

}
