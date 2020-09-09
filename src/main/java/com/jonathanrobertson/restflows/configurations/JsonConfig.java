package com.jonathanrobertson.restflows.configurations;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootConfiguration
public class JsonConfig {

	@Bean
	public JsonParser getJsonParser() {
		return JsonParserFactory.getJsonParser();
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
