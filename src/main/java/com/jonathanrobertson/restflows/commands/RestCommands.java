package com.jonathanrobertson.restflows.commands;

import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

import com.jonathanrobertson.restflows.services.MemoryService;

@ShellComponent
public class RestCommands {
	private static final StringJoiner	errJoiner		= new StringJoiner("; ", "[!] request failed", "");
	private static final StringJoiner	newlineJoiner	= new StringJoiner("\n");

	private final JsonParser	jsonParser;
	private final RestTemplate	rest;
	private final MemoryService	mem;

	public RestCommands(JsonParser jsonParser, RestTemplate rest, MemoryService mem) {
		this.jsonParser = jsonParser;
		this.rest = rest;
		this.mem = mem;
	}

	@ShellMethod("Retrieve GET from the provided place")
	public String get(String place) {
		return deserialize(request(HttpMethod.GET, place));
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

		return Optional.ofNullable(r.getHeaders().getContentType())
				.filter(type -> type.equalsTypeAndSubtype(MediaType.APPLICATION_JSON))
				.map(type -> {
					jsonParser.parseMap(r.getBody()).forEach((k, v) -> newlineJoiner.add(k + ": " + v));
					return newlineJoiner.toString();
				})
				.orElse(r.getBody());
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
		return rest.exchange(mem.getPlaces().get(place).getUriTemplate(), method, null, String.class, mem.getVars());
	}
}
