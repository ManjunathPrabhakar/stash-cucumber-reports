package com.github.manjunathprabhakar.moved.elk;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ESCredentials {
    private String esHostURL;
    private String esAPIKEY;
    private String esUserName;
    private String esPassword;
    private boolean ignoreSSL;
    private String esVersion; //Currently Supports 8.15.0, Future Releases Based on Requirement
}
