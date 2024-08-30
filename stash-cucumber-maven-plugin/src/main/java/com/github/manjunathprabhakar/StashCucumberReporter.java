package com.github.manjunathprabhakar;

import com.github.manjunathprabhakar.engine.Parser;
import com.github.manjunathprabhakar.moved.common.MojoLogger;
import com.github.manjunathprabhakar.moved.constants.For;
import com.github.manjunathprabhakar.moved.elk.ELKservice;
import com.github.manjunathprabhakar.moved.elk.ESCredentials;
import com.github.manjunathprabhakar.moved.pojos.out.StashOutPojos;
import lombok.Builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

@Builder
public class StashCucumberReporter {
    private String jsonPath;
    //    private Map<String, String> customData;
    private List<StashOutPojos> listenerData;
    private String elasticSearchPropFile;
    private String elasticSearchIndexName;

    public void start(For doFor) throws Exception {
        List<StashOutPojos> outData = null;
        if (doFor == For.MAVEN_PLUGIN)
            outData = doForPlugin(jsonPath);
        else if (doFor == For.CUKE_LISTENER)
            outData = doForCukeListener(listenerData);

        outData.stream().forEach(doc -> {
            try {
                new ELKservice().sendToElastic(ESCredentials.builder()
                                .esHostURL(getPropertyFileData(elasticSearchPropFile, "elasticSearchHost"))
                                .esAPIKEY(getPropertyFileData(elasticSearchPropFile, "elasticSearchAPIKey"))
                                .esUserName(getPropertyFileData(elasticSearchPropFile, "elasticSearchUserName"))
                                .esPassword(getPropertyFileData(elasticSearchPropFile, "elasticSearchPassword"))
                                .ignoreSSL(getPropertyFileData(elasticSearchPropFile, "elasticSearchIgnoreSSL").equalsIgnoreCase("true"))
                                .build(),
                        doc,
                        elasticSearchIndexName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private List<StashOutPojos> doForCukeListener(List<StashOutPojos> listenerData) {
        return listenerData;
    }

    private List<StashOutPojos> doForPlugin(String jsonPath) throws Exception {
        return Parser.parse(requireExistingNonNullorNomEmptyFile(jsonPath));
    }

    private String requireExistingNonNullorNomEmptyFile(String message) {
        if (message == null || message.isEmpty()) {
            //MojoLogger.getLogger().error(message);
            throw new NullPointerException(message);
        }
        File f = new File(message);
        if (!f.exists()) {
            //MojoLogger.getLogger().error(message);
            throw new RuntimeException(new FileNotFoundException(message));
        }
        return message;
    }

    public String getPropertyFileData(String elasticSearchCredsPropertiesFile, String propName) {
        String propData = "";
        if (!elasticSearchCredsPropertiesFile.equalsIgnoreCase("none")) {
            try (InputStream input = Files.newInputStream(Paths.get(elasticSearchCredsPropertiesFile))) {
                Properties prop = new Properties();
                // load a properties file
                prop.load(input);
                propData = prop.getProperty(propName);
            } catch (IOException ex) {
                MojoLogger.getLogger().error("Error while reading the properties files for Elastic Creds, " + ex);
                throw new RuntimeException("\nError while reading the properties files for Elastic Creds, " + ex);
            }
        }
        return propData;
    }

}
