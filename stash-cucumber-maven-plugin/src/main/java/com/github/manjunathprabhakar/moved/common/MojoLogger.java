package com.github.manjunathprabhakar.moved.common;

import lombok.Getter;
import lombok.Setter;
import org.apache.maven.plugin.logging.Log;

public class MojoLogger {
    @Getter
    @Setter
    private static Log logger;
}
