package com.inatel.stockquotemanager.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
@EnableAsync
@CacheConfig(cacheNames = "stocks")
public class CacheService {

    /**
     * verify cache is empty
     * clear cache
     * populate cache
     *
     *
     */

    private CacheManager cacheManager;

    public CacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public boolean verifyCacheIsEmpty() {

        ConcurrentHashMap cache = (ConcurrentHashMap) cacheManager.getCache("stocks").getNativeCache();

        return cache.isEmpty();

    }

    @Async
    public CompletableFuture<String> clearCache() {

        return CompletableFuture.supplyAsync(() -> {

            try {

                Objects.requireNonNull(this.cacheManager.getCache("stocks")).clear();

            } catch (Exception ignored) {
            }

            return "ok";
        });
    }

    @Async
    public void populateCache(String data) throws JsonProcessingException {

        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        JsonNode rootNode = mapper.readTree(data);
        Iterator<Map.Entry<String, JsonNode>> fieldsIterator = rootNode.fields();

        while (fieldsIterator.hasNext()) {

            Map.Entry<String, JsonNode> field = fieldsIterator.next();

            try {

                cacheManager.getCache("stocks").put(field.getKey(), field.getValue().toString());

            } catch (Exception ignored) {
            }

        }

    }

    public boolean verifyCacheKeyExists(String key) {

        String desc = "";

        Cache.ValueWrapper valueWrapper = cacheManager.getCache("stocks").get(key);

        if (valueWrapper != null) {

            desc = String.valueOf(String.valueOf(valueWrapper.get()));

            return true;
        }

        return false;

    }

}