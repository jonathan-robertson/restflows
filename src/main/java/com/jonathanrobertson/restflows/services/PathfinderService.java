package com.jonathanrobertson.restflows.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class PathfinderService {
    private static final String pattern = "[.\\[]|]\\.";

    private final ObjectMapper objectMapper;

    public PathfinderService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Object follow(String json, String path) {
        try {
            return follow(objectMapper.readTree(json), new ArrayList<>(Arrays.asList(path.split(pattern))));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("unable to follow content", e);
        }
    }

    private Object follow(JsonNode node, ArrayList<String> path) {
        switch (node.getNodeType()) {
            case ARRAY:
                return follow(node.get(Integer.parseInt(path.remove(0))), path);
            case BINARY:
                return node.asInt();
            case BOOLEAN:
                return node.asBoolean();
            case MISSING:
                throw new RuntimeException("missing node encountered");
            case NULL:
                return null;
            case NUMBER:
                switch (node.numberType()) {
                    case INT:
                        return node.asInt();
                    case LONG:
                    case BIG_INTEGER:
                        return node.asLong();
                    case FLOAT:
                    case DOUBLE:
                    case BIG_DECIMAL:
                        return node.asDouble();
                }
            case OBJECT:
                return follow(node.get(path.remove(0)), path);
            case POJO:
                return node;
            case STRING:
                return node.asText();
            default:
                throw new RuntimeException("failed to interpret JSON");
        }
    }
}
