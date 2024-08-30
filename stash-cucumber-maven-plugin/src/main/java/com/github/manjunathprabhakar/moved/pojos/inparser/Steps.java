package com.github.manjunathprabhakar.moved.pojos.inparser;

import com.google.gson.annotations.SerializedName;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Manjunath Prabhakar (Manjunath-PC)
 * @created 19/09/2020
 * @project cooker-cucumber-reporter
 *
 * <p> Holds Step details </p>
 */
public class Steps {

    @SerializedName(value = "output")
    private List<String> output = new ArrayList<String>();

    @SerializedName(value = "embeddings")
    private List<Embeddings> embeddings = new ArrayList<Embeddings>();

    @SerializedName(value = "result")
    private Result result;

    @SerializedName(value = "before")
    private List<BeforeHook> before = new ArrayList<BeforeHook>();

    @SerializedName(value = "after")
    private List<AfterHook> after = new ArrayList<AfterHook>();

    @SerializedName(value = "line")
    private String line = "";

    @SerializedName(value = "name")
    private String name = "";

    @SerializedName(value = "match")
    private Match match;

    @SerializedName(value = "keyword")
    private String keyword;

    @SerializedName(value = "rows")
    private List<DataTableRows> rows = new ArrayList<DataTableRows>();

    @SerializedName(value = "doc_string")
    private DocStrings docStrings;

    public DocStrings getDocStrings() {
        return docStrings;
    }

    public List<String> getOutput() {
        return output;
    }

    public void setOutput(List<String> output) {
        this.output = output;
    }

    public List<Embeddings> getEmbeddings() {
        return embeddings;
    }

    public void setEmbeddings(List<Embeddings> embeddings) {
        this.embeddings = embeddings;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public List<BeforeHook> getBefore() {
        return before;
    }

    public void setBefore(List<BeforeHook> before) {
        this.before = before;
    }

    public List<AfterHook> getAfter() {
        return after;
    }

    public void setAfter(List<AfterHook> after) {
        this.after = after;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<DataTableRows> getRows() {
        return rows;
    }

    public void setRows(List<DataTableRows> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "Step{" +
                "output=" + output +
                ", embeddings=" + embeddings +
                ", result=" + result +
                ", before=" + before +
                ", after=" + after +
                ", line='" + line + '\'' +
                ", name='" + name + '\'' +
                ", match=" + match +
                ", keyword='" + keyword + '\'' +
                ", rows=" + rows +
                '}';
    }

    public Duration getDuration() {
        return Duration.ofNanos(result.getDuration());
    }

    public String getDurationStringFormat(Duration duration) {

        long days = duration.toDays();
        duration = duration.minusDays(days);
        long hours = duration.toHours();
        duration = duration.minusHours(hours);
        long mins = duration.toMinutes();
        duration = duration.minusMinutes(mins);
        long secs = duration.getSeconds();
        duration = duration.minusSeconds(secs);
        long mils = duration.toMillis();

        String res = ((days > 0 ? (days + "d") : "") + " " +
                (hours > 0 ? (String.format("%02d", hours) + "h") : "") + " " +
                (mins > 0 ? (String.format("%02d", mins) + "m") : "") + " " +
                (secs > 0 ? (String.format("%02d", secs) + "s") : "") + " " +
                (mils > 0 ? (String.format("%04d", secs).substring(0, 3) + "ms") : "")).trim();

        return res.isEmpty() ? "0ms" : res;
    }
}
