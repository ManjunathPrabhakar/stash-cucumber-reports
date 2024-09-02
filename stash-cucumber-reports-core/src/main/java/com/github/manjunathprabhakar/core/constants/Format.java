package com.github.manjunathprabhakar.core.constants;

import lombok.Getter;

public enum Format {
    DATE("dd-MMM-yyyy"),
    TIME("HH:mm:ss.SSS"),
    AUDIT_DATE("dd-MMM-yyyy"),
    AUDIT_TIME("HH:mm:ss.SSS z");

    @Getter
    private String format;

    Format(String s) {
        format = s;
    }
}
