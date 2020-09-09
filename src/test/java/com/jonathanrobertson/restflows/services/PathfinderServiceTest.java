package com.jonathanrobertson.restflows.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jonathanrobertson.restflows.configurations.JsonConfig;

@SpringBootTest(classes = { PathfinderService.class, JsonConfig.class })
public class PathfinderServiceTest {
	private static final String TEXT = "{ \"request\": { \"args\": [ { \"foo\": \"dance\" }, { \"foo\": \"at\" }, { \"foo\": \"midnight\" } ] }, \"response\": { \"status\": 204 } }";

	@Autowired
	private PathfinderService pathfinderService;

	@Test
	public void followValueTest() {
		Assertions.assertEquals("midnight", pathfinderService.follow(TEXT, "request.args[2].foo"));
	}

	@Test
	public void followObjectTest() {
		Assertions.assertEquals("{\"status\":204}", pathfinderService.follow(TEXT, "response").toString());
	}
}
