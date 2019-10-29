package com.jonathanrobertson.restflows.services;

import com.jonathanrobertson.restflows.configurations.ObjectMapperConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PathfinderService.class, ObjectMapperConfig.class})
public class PathfinderServiceTest {
    private static final String TEXT = "{ \"request\": { \"args\": [ { \"foo\": \"dance\" }, { \"foo\": \"at\" }, { \"foo\": \"midnight\" } ] } }";

    @Autowired
    private PathfinderService pathfinderService;

    @Test
    public void follow() {
        Assert.assertEquals("midnight", pathfinderService.follow(TEXT, "request.args[2].foo"));
    }
}