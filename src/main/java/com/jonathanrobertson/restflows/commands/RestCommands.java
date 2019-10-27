package com.jonathanrobertson.restflows.commands;

import com.jonathanrobertson.restflows.services.MemoryService;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.print.attribute.standard.Media;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

@ShellComponent
public class RestCommands {
    private static final StringJoiner errJoiner = new StringJoiner("; ", "[!] request failed", "");

    private final JsonParser jsonParser;
    private final RestTemplate rest;
    private final MemoryService mem;

    public RestCommands(JsonParser jsonParser, RestTemplate rest, MemoryService mem) {
        this.jsonParser = jsonParser;
        this.rest = rest;
        this.mem = mem;
    }

    // GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE

    @ShellMethod("Retrieve GET from the provided place")
    public String get(String place) {
        return deserialize(request(HttpMethod.GET, place));
    }

    private String deserialize(ResponseEntity<String> r) {
        Objects.requireNonNull(r);

        if (!r.hasBody()) {
            return "[no body provided in response]";
        }



        return Optional.ofNullable(r.getHeaders().getContentType())
                .filter(type -> type.equalsTypeAndSubtype(MediaType.APPLICATION_JSON))
                .map(type -> {
                    StringJoiner joiner = new StringJoiner("\n");
                    jsonParser.parseMap(r.getBody()).forEach((k, v) -> joiner.add(k + ": " + v));
                    return joiner.toString();
                })
                .orElse(r.getBody());

//        if (type.equalsTypeAndSubtype(MediaType.APPLICATION_JSON)) {
//
//        }

//        if (r.getHeaders().getContentType().equalsTypeAndSubtype(MediaType.APPLICATION_JSON))

//        switch () {
//            case MediaType.APPLICATION_JSON:
//            case MediaType.APPLICATION_JSON_UTF8:
//                return "application/json"; // jsonParser.parseMap(r.getBody()).toString();
//            default:
//                return r.getBody();
//        }
    }

    @ShellMethod("Retrieve HEAD from the provided place")
    public String head(String place) {
        return request(HttpMethod.HEAD, place).getHeaders().toString();
    }

    @ShellMethod("Retrieve POST from the provided place")
    public String post(String place, String body) { // TODO
        return request(HttpMethod.POST, place).getBody();
    }

    @ShellMethod("Retrieve PUT from the provided place")
    public String put(String place, String body) { // TODO
        return request(HttpMethod.PUT, place).getBody();
    }

    @ShellMethod("Retrieve PATCH from the provided place")
    public String patch(String place, String body) { // TODO
        return request(HttpMethod.PATCH, place).getBody();
    }

    @ShellMethod("Retrieve DELETE from the provided place")
    public String delete(String place) {
        return request(HttpMethod.DELETE, place).getBody();
    }

    @ShellMethod("Retrieve OPTIONS from the provided place")
    public String options(String place) {
        return request(HttpMethod.OPTIONS, place).getBody();
    }

    @ShellMethod("Retrieve TRACE from the provided place")
    public String trace(String place) {
        return request(HttpMethod.TRACE, place).getBody();
    }

    private ResponseEntity<String> request(HttpMethod method, String place) {
        // TODO: check out https://docs.postman-echo.com/?version=latest
        ResponseEntity<String> response = rest.exchange(mem.places.get(place).getUriTemplate(), method, null, String.class, mem.vars);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw HttpClientErrorException.create(
                    response.getStatusCode(),
                    response.getStatusCode().getReasonPhrase(),
                    response.getHeaders(),
                    Optional.ofNullable(response.getBody()).map(String::getBytes).get(),
                    null);
//            throw new HttpClientErrorException(response.getStatusCode(), errJoiner
//                    .add("method:" + method)
//                    .add("uriTemplate:" + mem.places.get(place).getUriTemplate())
//                    .add("body:" + response.getBody())
//                    .toString());
        }

        return response;

//        if (response.hasBody()) {
//            return Optional.ofNullable(response.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE))
//                    .filter(s -> s.equals("application/json"))
//                    .map(s -> "blork")
//                    .orElse(response.getBody());
//        } else {
//            return "[no body provided in response]";
//        }
    }
}