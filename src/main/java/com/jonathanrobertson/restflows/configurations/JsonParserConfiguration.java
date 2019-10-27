package com.jonathanrobertson.restflows.configurations;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class JsonParserConfiguration {

    @Bean
    public JsonParser getJsonParser() {
        return JsonParserFactory.getJsonParser();
    }
}
