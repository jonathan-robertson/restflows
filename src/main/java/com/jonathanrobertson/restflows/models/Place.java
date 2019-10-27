package com.jonathanrobertson.restflows.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode
public class Place {
    private final String uriTemplate;
}
