package com.jonathanrobertson.restflows.models;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Memory {
	private final Map<String, String>	vars	= new HashMap<>();
	private final Map<String, Place>	places	= new HashMap<>();
}
