package com.jonathanrobertson.restflows.services;

import com.jonathanrobertson.restflows.models.Place;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@ToString
public class MemoryService {
    public final Map<String, String> vars = new HashMap<>();
    public final Map<String, Place> places = new HashMap<>();
}
