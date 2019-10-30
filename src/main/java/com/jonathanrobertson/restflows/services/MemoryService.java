package com.jonathanrobertson.restflows.services;

import com.jonathanrobertson.restflows.models.Place;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MemoryService {
    public final Map<String, String> vars = new HashMap<>();
    public final Map<String, Place> places = new HashMap<>();
}
