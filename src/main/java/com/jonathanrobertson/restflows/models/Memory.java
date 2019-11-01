package com.jonathanrobertson.restflows.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class Memory {
    private final Map<String, String> vars = new HashMap<>();
    private final Map<String, Place> places = new HashMap<>();
}
