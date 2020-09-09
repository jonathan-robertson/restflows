package com.jonathanrobertson.restflows.converters;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HttpHeadersConverter implements Converter<String, HttpHeaders> {

	@Override
	@SneakyThrows
	public HttpHeaders convert(String source) {

		// TODO: maybe refactor later with Stream & custom Collector
		HttpHeaders headers = new HttpHeaders();
		for (String s : source.split(",")) {
			String[] pair = Optional.of(s.split(":"))
					.filter(a -> a.length == 2)
					.orElseThrow(() -> new IllegalArgumentException("invalid header param received"));
			headers.add(pair[0].strip(), pair[1].strip());
		}

		return headers;
	}
}
