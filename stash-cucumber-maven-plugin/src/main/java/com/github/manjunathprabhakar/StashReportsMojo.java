package com.github.manjunathprabhakar;

import com.github.manjunathprabhakar.moved.constants.For;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.Map;

@Mojo(name = "begin-stash", threadSafe = true, defaultPhase = LifecyclePhase.POST_INTEGRATION_TEST)
public class StashReportsMojo extends AbstractMojo {

    @Parameter(property = "jsonPath", required = true, defaultValue = "none")
    private String jsonPath;

    @Parameter(property = "elasticSearchCredsPropertiesFile", required = true, defaultValue = "none")
    private String elasticSearchCredsPropertiesFile;

    @Parameter(property = "elasticSearchIndexName", required = false, defaultValue = "betta")
    private String elasticSearchIndexName;

    @Parameter(property = "customData", defaultValue = "")
    private Map<String, String> customData;

    @Parameter(property = "skip", defaultValue = "false")
    private String skip;

    public static void main(String[] args) throws Exception {
        StashReportsMojo s = new StashReportsMojo();
        s.jsonPath = "stash-cucumber-maven-plugin/json";
        s.elasticSearchCredsPropertiesFile = System.getProperty("user.home") + "\\escreds.properties";
        s.elasticSearchIndexName = "betta";
        s.execute();
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skip.equalsIgnoreCase("true")) {
            System.out.println("Stash Cucumber maven plugin usage has been skipped with skip=true! ");
            return;
        }

        try {
            StashCucumberReporter.builder()
                    .jsonPath(this.jsonPath)
                    //                .customData(this.customData)
                    .elasticSearchPropFile(this.elasticSearchCredsPropertiesFile)
                    .elasticSearchIndexName(this.elasticSearchIndexName)
                    .build()
                    .start(For.MAVEN_PLUGIN);
        } catch (Exception e) {
            throw new MojoExecutionException(e);
        }
    }

}
