package com.jonathanrobertson.restflows.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonathanrobertson.restflows.enums.CommandContext;
import com.jonathanrobertson.restflows.models.Place;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@Service
public class MemoryService {
    public final Map<String, String> vars = new HashMap<>();
    public final Map<String, Place> places = new HashMap<>();

    private ObjectMapper objectMapper;

    public MemoryService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String toString() {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("failed to write object", e);
        }
    }
}
