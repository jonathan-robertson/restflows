package com.jonathanrobertson.restflows.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jonathanrobertson.restflows.configurations.ObjectMapperConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { PathfinderService.class, ObjectMapperConfig.class })
public class PathfinderServiceTest {
	private static final String TEXT = "{ \"request\": { \"args\": [ { \"foo\": \"dance\" }, { \"foo\": \"at\" }, { \"foo\": \"midnight\" } ] }, \"response\": { \"status\": 204 } }";

	@Autowired
	private PathfinderService pathfinderService;

	@Test
	public void followValueTest() {
		Assert.assertEquals("midnight", pathfinderService.follow(TEXT, "request.args[2].foo"));
	}

	@Test
	public void followObjectTest() {
		Assert.assertEquals("{\"status\":204}", pathfinderService.follow(TEXT, "response").toString());
	}
}
