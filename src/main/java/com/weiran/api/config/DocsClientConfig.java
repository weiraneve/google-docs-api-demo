package com.weiran.api.config;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.docs.v1.Docs;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DocsClientConfig {

    @Bean
    public Docs docsClient() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new ClassPathResource("/service_account_key.json").getInputStream())
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform","https://www.googleapis.com/auth/documents"));

        return new Docs.Builder
                (new NetHttpTransport(), new GsonFactory(), new HttpCredentialsAdapter(credentials))
                .setApplicationName("google-docs-api-example").build();
    }

}
