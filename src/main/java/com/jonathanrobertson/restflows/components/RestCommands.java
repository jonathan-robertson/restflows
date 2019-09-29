package com.jonathanrobertson.restflows.components;

import org.springframework.http.HttpMethod;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

@ShellComponent
public class RestCommands {

    public final RestTemplate restTemplate;

    public RestCommands(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @ShellMethod("Retrieve OPTIONS from google.com")
    public String get() {
        String url = "https://jsonplaceholder.typicode.com/posts/1";
        return restTemplate.exchange(url, HttpMethod.GET, null, String.class).getBody();
    }
}