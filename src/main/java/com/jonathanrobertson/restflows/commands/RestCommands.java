package com.jonathanrobertson.restflows.commands;

import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

import lombok.AllArgsConstructor;

import org.springframework.boot.json.JsonParser;
import org.springframework.http.*;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

import com.jonathanrobertson.restflows.services.MemoryService;

@ShellComponent
@AllArgsConstructor
public class RestCommands {
	private static final StringJoiner errJoiner = new StringJoiner("; ", "[!] request failed", "");

	private final JsonParser	jsonParser;
	private final RestTemplate	rest;
	private final MemoryService	mem;

	@ShellMethod("Submit a REST request to the provided place")
	public String go(String method, String place/*
												 * , @ShellOption(defaultValue = "__NONE__") String
												 * body, @ShellOption(defaultValue = "__NONE__") MultiValueMap<String, String>
												 * headers
												 */) {
		return deserialize(request(method, place, /* new HttpEntity<>(body, headers) */ null));
	}

	@ShellMethod("Retrieve GET from the provided place")
	public String get(String place) {
		return deserialize(request("get", place, null));
	}

	@ShellMethod("Retrieve HEAD from the provided place")
	public String head(String place) {
		return request("head", place, null).getHeaders().toString();
	}

	@ShellMethod("Retrieve POST from the provided place")
	public String post(String place, String body) { // TODO
		return request("post", place, null).getBody();
	}

	@ShellMethod("Retrieve PUT from the provided place")
	public String put(String place, String body) { // TODO
		return request("put", place, null).getBody();
	}

	@ShellMethod("Retrieve PATCH from the provided place")
	public String patch(String place, String body) { // TODO
		return request("patch", place, null).getBody();
	}

	@ShellMethod("Retrieve DELETE from the provided place")
	public String delete(String place) {
		return request("delete", place, null).getBody();
	}

	@ShellMethod("Retrieve OPTIONS from the provided place")
	public String options(String place) {
		return request("options", place, null).getBody();
	}

	@ShellMethod("Retrieve TRACE from the provided place")
	public String trace(String place) {
		return request("trace", place, null).getBody();
	}

	protected Object follow(String data, String key) {
		return jsonParser.parseMap(data).get(key);
	}

	protected Object follow(String data, int index) {
		return jsonParser.parseList(data).get(index);
	}

	private String deserialize(ResponseEntity<String> r) {
		Objects.requireNonNull(r);

		if (!r.hasBody()) {
			return "[no body provided in response]";
		}

		return Optional.of(r)
				.map(ResponseEntity::getHeaders)
				.map(HttpHeaders::getContentType)
				.filter(type -> type.equalsTypeAndSubtype(MediaType.APPLICATION_JSON))
				.isPresent()
						? processBody(r.getBody())
						: r.getBody();
	}

	protected String processBody(String body) {
		final StringJoiner newlineJoiner = new StringJoiner("\n");
		jsonParser.parseMap(body).forEach((k, v) -> newlineJoiner.add(k + ": " + v));
		return newlineJoiner.toString();
	}

	// TODO: check out https://docs.postman-echo.com/?version=latest
	private ResponseEntity<String> request(String method, String place, HttpEntity<String> requestEntity) {
		HttpMethod httpMethod = HttpMethod.valueOf(method.toUpperCase());
		String uriTemplate = mem.getPlaces().get(place).getUriTemplate();
		return rest.exchange(uriTemplate, httpMethod, requestEntity, String.class, mem.getVars());
	}
}
