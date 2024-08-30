package com.github.manjunathprabhakar.core.elk;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.github.manjunathprabhakar.core.exceptions.StashElasticException;
import com.github.manjunathprabhakar.core.pojos.out.StashOutPojos;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Date;
import java.util.UUID;

public class ELKservice {

    public String randomID() {
        return UUID.randomUUID() + (new SimpleDateFormat("MMddyyyyGHHmmssSSSz").format(new Date())) + "-" + Clock.systemDefaultZone().instant().getNano();
    }

    public void sendToElastic(ESCredentials creds, StashOutPojos data, String indexName) throws IOException {
//        String apiKey = "RTFUWGxKRUJtUXpnSWh1VTd6SVM6XzBmYXJaWTVUMkdVbnRaWTVxaXE0Zw==";
//        String serverUrl = "https://localhost:9200";


        ElasticsearchTransport transport = null;
        try {
            RestClientBuilder authorization = RestClient
                    .builder(HttpHost.create(creds.getEsHostURL()))
                    .setDefaultHeaders(new Header[]{
                            new BasicHeader("Authorization", "ApiKey " + creds.getEsAPIKEY())
                    });
            RestClient restClient;
            if (creds.isIgnoreSSL()) {
                restClient = authorization.setHttpClientConfigCallback(h -> {
                    try {
                        return h.setSSLContext(ignoreCert());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).build();
            } else {
                restClient = authorization.build();
            }


            // Create the transport with a Jackson mapper
            transport = new RestClientTransport(
                    restClient, new JacksonJsonpMapper());

            // And create the API client
            ElasticsearchClient esClient = new ElasticsearchClient(transport);

            IndexResponse response = esClient.index(i -> i
                            .index(indexName)
//                .id(
//                        randomID()
//                )
                            .document(data)
            );

            if (response.version() != 1) {
                throw new Exception("ES Document Creation did not return Created!, Returned " + response.version() + " " + response.result().jsonValue());
            }
//            System.out.println("response.version() = " + response.version());
//            System.out.println("response.result().jsonValue() = " + response.result().jsonValue());
        } catch (Exception e) {
            throw new StashElasticException("ISSUE WHILE REACHING OUT TO ELASTIC SEARCH, CHECK ALL CREDENTIALS\n" + e);
        } finally {
            transport.close();
        }


        transport.close();

    }

    private SSLContext ignoreCert() throws Exception {
        try {
            SSLContextBuilder sslBuilder = SSLContexts.custom()
                    .loadTrustMaterial(null, (x509Certificates, s) -> true);
            return sslBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
