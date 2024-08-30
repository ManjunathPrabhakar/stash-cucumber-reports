package com.github.manjunathprabhakar.todelete;

import com.github.manjunathprabhakar.moved.common.MojoLogger;
import com.github.manjunathprabhakar.moved.elk.ESCredentials;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;


public class XXXX extends AbstractMojo {

    private Log LOGGER = getLog();


    @Parameter(property = "jsonPath", required = true, defaultValue = "none")
    private String jsonPath;

    @Parameter(property = "elasticSearchIgnoreSSL", required = false, defaultValue = "false")
    private String elasticSearchIgnoreSSL;

    @Parameter(property = "elasticSearchCredsPropertiesFile", required = false, defaultValue = "none")
    private String elasticSearchCredsPropertiesFile;

    public static String requireStringNonNullorEmpty(String obj, String message) {
        if (obj == null || obj.isEmpty()) {
            MojoLogger.getLogger().error(message);
            throw new NullPointerException(message);
        }
        return obj;
    }

    public static void main(String[] args) throws Exception {

        /*

        Input from user

        Mandatory

        * Elastic Search URL
        * Elastic Search API Key / Username & Password
        * JSONS Path

        Optional

        * Elastic Search Ignore SSL -> DEFAULT FALSE
        * Index Name in ES -> DEFAULT stash_data
        * Data Needed to Create Document in ES -> DEFAULT StashOutPOJO.java

         */


        ESCredentials creds = ESCredentials.builder()
                .esAPIKEY("VTFRM2xaRUJtUXpnSWh1VVl6Sk86MWVIeEczeHpTN0tMT2lZRm5sQWFCdw==")
                .esHostURL("https://localhost:9200")
                .ignoreSSL(true)
                .build();

//        new Parser().parse("stash-cucumber-maven-plugin/json", creds);
    }

    /**
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

//        String esHostName, esapiKey = "", esUserName = "", esPassword = "";
//        boolean esIgnoreSSL = false, isAPIKey = false;
//
//        MojoLogger.setLogger(LOGGER);
//
//        /*
//        Check if system properties has ES Hostname, API Key or uname : password
//         */
//
//        if (elasticSearchIgnoreSSL.equalsIgnoreCase("true")) {
//            esIgnoreSSL = true;
//        }
//
//
//        Map<String, String> sysproperties = System.getenv();
//        System.out.println("sysproperties = " + sysproperties);
//        if (!(sysproperties.get("elasticSearchHost") == null || sysproperties.get("elasticSearchHost").isEmpty())) {
//            esHostName = System.getProperty("elasticSearchHost");
//        } else if (!(getPropertyFileData(elasticSearchCredsPropertiesFile, "elasticSearchHost") == null
//                || getPropertyFileData(elasticSearchCredsPropertiesFile, "elasticSearchHost").isEmpty())) {
//            esHostName = getPropertyFileData(elasticSearchCredsPropertiesFile, "elasticSearchHost");
//        } else {
//            getLog().warn("elasticSearchHost is not found as System property or in provided properties file");
//            throw new RuntimeException("\nelasticSearchHost is not found as System property or in provided properties file");
//        }
////elasticSearchAPIKey=VTFRM2xaRUJtUXpnSWh1VVl6Sk86MWVIeEczeHpTN0tMT2lZRm5sQWFCdw==
//        if (!(sysproperties.get("elasticSearchAPIKey") == null || sysproperties.get("elasticSearchAPIKey").isEmpty())) {
//            esapiKey = System.getProperty("elasticSearchAPIKey");
//            isAPIKey = true;
//        } else if (!(getPropertyFileData(elasticSearchCredsPropertiesFile, "elasticSearchAPIKey") == null
//                || getPropertyFileData(elasticSearchCredsPropertiesFile, "elasticSearchAPIKey").isEmpty())) {
//            esapiKey = getPropertyFileData(elasticSearchCredsPropertiesFile, "elasticSearchAPIKey");
//            isAPIKey = true;
//        } else {
//            getLog().warn("elasticSearchAPIKey is not found as System property or in provided properties file, checking if username & password available");
//            /////////
//            if (!(sysproperties.get("elasticSearchUserName") == null || sysproperties.get("elasticSearchUserName").isEmpty())
//                    && !(sysproperties.get("elasticSearchPassword") == null || sysproperties.get("elasticSearchPassword").isEmpty())) {
//                esUserName = System.getProperty("elasticSearchUserName");
//                esPassword = System.getProperty("elasticSearchPassword");
//            } else if (!(
//                    (getPropertyFileData(elasticSearchCredsPropertiesFile, "elasticSearchUserName") == null
//                            || getPropertyFileData(elasticSearchCredsPropertiesFile, "elasticSearchUserName").isEmpty())
//                            && (getPropertyFileData(elasticSearchCredsPropertiesFile, "elasticSearchPassword") == null
//                            || getPropertyFileData(elasticSearchCredsPropertiesFile, "elasticSearchPassword").isEmpty())
//            )) {
//                esUserName = getPropertyFileData(elasticSearchCredsPropertiesFile, "elasticSearchUserName");
//                esPassword = getPropertyFileData(elasticSearchCredsPropertiesFile, "elasticSearchPassword");
//            } else {
//                getLog().warn("elasticSearchUserName & elasticSearchPassword is not found as System property or in provided properties file");
//                throw new RuntimeException("\nelasticSearchUserName & elasticSearchPassword is not found as System property or in provided properties file");
//            }
//            //////////
//        }

//
//        ESCredentials.ESCredentialsBuilder esCredentialsBuilder = ESCredentials.builder()
//                .esHostURL(esHostName).ignoreSSL(esIgnoreSSL);
//        if (isAPIKey) {
//            esCredentialsBuilder = esCredentialsBuilder
//                    .esAPIKEY(esapiKey);
//        } else {
//            esCredentialsBuilder = esCredentialsBuilder
//                    .esUserName(esUserName).esPassword(esPassword);
//        }
//        try {
//            new MainExecutor().perform(jsonPath, esCredentialsBuilder.build());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//


        try {
//            new Parser().parse(jsonPath, ESCredentials.builder().
//                    esAPIKEY("VTFRM2xaRUJtUXpnSWh1VVl6Sk86MWVIeEczeHpTN0tMT2lZRm5sQWFCdw==")
//                    .esHostURL("https://localhost:9200").
//                    ignoreSSL(true).
//                    build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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
