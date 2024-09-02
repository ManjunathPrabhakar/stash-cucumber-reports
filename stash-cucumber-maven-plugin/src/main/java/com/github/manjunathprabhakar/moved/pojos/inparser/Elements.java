package com.github.manjunathprabhakar.moved.pojos.inparser;

import com.google.gson.annotations.SerializedName;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Manjunath Prabhakar (Manjunath-PC)
 * @created 19/09/2020
 * @project cooker-cucumber-reporter
 *
 * <p>This is used ot hold Elements, An Element can be Background or Scenario</p>
 */
public class Elements {

    @SerializedName(value = "line")
    private int line;

    @SerializedName(value = "name")
    private String name = "";

    @SerializedName(value = "id")
    private String id = "";

    @SerializedName(value = "description")
    private String description = "";

    @SerializedName(value = "type")
    private String type = "";

    @SerializedName(value = "keyword")
    private String keyword = "";

    @SerializedName(value = "steps")
    private List<Steps> steps = new ArrayList<Steps>();

    @SerializedName(value = "tags")
    private List<Tags> tags = new ArrayList<Tags>();

    @SerializedName(value = "start_timestamp")
    private String start_timestamp = "";

    @SerializedName(value = "before")
    private List<BeforeHook> before = new ArrayList<BeforeHook>();

    @SerializedName(value = "after")
    private List<AfterHook> after = new ArrayList<AfterHook>();

    private transient int scenarioIndex = 0;

    private transient String scenarioResult = "";

    public int getScenarioIndex() {
        return scenarioIndex;
    }

    public void setScenarioIndex(final int scenarioIndex) {
        this.scenarioIndex = scenarioIndex;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public String getStart_timestamp() {
        return start_timestamp;
    }

    public void setStart_timestamp(String start_timestamp) {
        this.start_timestamp = start_timestamp;
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

    public boolean isScenario() {
        return type.equalsIgnoreCase("scenario");
    }

//    public String getScenarioResult() {
//        String res = Status.UNKNOWN.getStatus();
//
//        if (type.equalsIgnoreCase("scenario")) {
//            Map<String, Integer> statuses = new HashMap<>();
//            for (Steps s : steps) {
//                String temp = s.getResult().getStatus();
//                if (statuses.containsKey(temp)) {
//                    int num = statuses.get(temp);
//                    num = num + 1;
//                    statuses.put(temp, num);
//                } else {
//                    statuses.put(temp, 1);
//                }
//            }
//
//            if (statuses.containsKey("failed")) {
//                res = Status.FAILED.getStatus();
//            } else if (statuses.containsKey("skipped")) {
//                res = Status.SKIPPED.getStatus();
//            } else if (statuses.containsKey("pending")) {
//                res = Status.PENDING.getStatus();
//            } else if (statuses.containsKey("unused")) {
//                res = Status.UNUSED.getStatus();
//            } else if (statuses.containsKey("ambiguous")) {
//                res = Status.AMBIGUOUS.getStatus();
//            } else if (statuses.containsKey("undefined")) {
//                res = Status.UNDEFINED.getStatus();
//            } else if (statuses.containsKey("passed")) {
//                res = Status.PASSED.getStatus();
//            }
//
//        }
//
//        return res.toUpperCase();
//    }

    public Duration getDuration() {
        Duration duration = Duration.ofNanos(0);
        for (Steps step : steps) {
            duration = duration.plus(step.getDuration());
        }
        if (before != null) {
            for (BeforeHook hook : before) {
                duration = duration.plus(hook.getDuration());
            }
        }
        if (after != null) {
            for (AfterHook hook : after) {
                duration = duration.plus(hook.getDuration());
            }
        }
        return duration;
    }

    public String getStatus() {
        String res = "other";

        if (steps.stream().anyMatch(s -> s.getResult().getStatus().equalsIgnoreCase("failed"))) {
            res = "failed";
        } else if (steps.stream().anyMatch(s -> s.getResult().getStatus().equalsIgnoreCase("skipped"))) {
            res = "skipped";
        } else if (steps.stream().allMatch(s -> s.getResult().getStatus().equalsIgnoreCase("passed"))) {
            res = "passed";
        }

        return res;
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

    public Map<String, String> getScenariosStatusesCount() {
        Map<String, String> stat = new HashMap<>();

        stat.put("passed", "" + steps.stream().filter(s -> getType().equalsIgnoreCase("scenario") & s.getResult().getStatus().equalsIgnoreCase("passed")).count());
        stat.put("failed", "" + steps.stream().filter(s -> getType().equalsIgnoreCase("scenario") & s.getResult().getStatus().equalsIgnoreCase("failed")).count());
        stat.put("skipped", "" + steps.stream().filter(s -> getType().equalsIgnoreCase("scenario") & s.getResult().getStatus().equalsIgnoreCase("skipped")).count());
        stat.put("other", "" + steps.stream().filter(s -> getType().equalsIgnoreCase("scenario") & (!s.getResult().getStatus().equalsIgnoreCase("passed") & !s.getResult().getStatus().equalsIgnoreCase("failed") & !s.getResult().getStatus().equalsIgnoreCase("skipped"))).count());

        return stat;
    }


}
