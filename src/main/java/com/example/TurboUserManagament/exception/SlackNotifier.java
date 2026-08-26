package com.example.TurboUserManagament.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.Map;

@Component
public class SlackNotifier {
    private RestClient restClient;
    private final String webhookURL;

    public SlackNotifier(
                         @Value("${slack.webhook-url}")
                        String url){
        this.restClient = RestClient.builder().build();
        this.webhookURL=url;
    }

    public void send(String message){
        restClient.post()
                .uri(webhookURL)
                .body(Map.of("text",message))
                .retrieve()
                .toBodilessEntity();
    }
}
