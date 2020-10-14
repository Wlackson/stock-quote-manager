package com.inatel.stockquotemanager.service;

import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wiremock.net.minidev.json.JSONObject;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

@Service
public class RestClientService {

    private static final String THIS_HOST_URL = "localhost/api/v1/quotes";
    private static final String THIS_HOST_PORT = "8081";
    private static final String SUBSCRIBE_TO_STOCK_CHANGES = "http://localhost:8080/notification";
    private static final String GET_STOCKS = "http://localhost:8080/stock";

    RestTemplate restTemplate = new RestTemplate();

    public boolean subscribeToStockChanges() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject requestJson = new JSONObject();
        requestJson.put("host", THIS_HOST_URL);
        requestJson.put("port", THIS_HOST_PORT);

        HttpEntity<String> entity = new HttpEntity<>(requestJson.toString(), headers);
        boolean subscribed = false;

        try {

            ResponseEntity<String> response = restTemplate.postForEntity(SUBSCRIBE_TO_STOCK_CHANGES, entity, String.class);
            HttpStatus statusCode = response.getStatusCode();

            if (statusCode == HttpStatus.CREATED) {
                subscribed = true;
            }

        } catch (Exception ignored) {
        }

        return subscribed;

    }

    @Async
    public CompletableFuture<String> getAllStocks() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        return CompletableFuture.supplyAsync(() -> {

            ResponseEntity<String> result = null;
            HttpStatus statusCode = null;

            try {

                result = restTemplate.exchange(GET_STOCKS, HttpMethod.GET, entity, String.class);
                statusCode = result.getStatusCode();

            } catch (Exception ignored) {
            }

            assert statusCode == HttpStatus.OK;
            return result.getBody();

        });

    }

}
