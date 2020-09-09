package com.jonathanrobertson.restflows.converters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;

@SpringBootTest(classes = { HttpHeadersConverter.class })
class HttpHeadersConverterTest {

	@Autowired
	HttpHeadersConverter target;

	@Test
	void convert() {
		final String source = "Content-Type: application/json,scope:15,     scope    :  twelve!";

		HttpHeaders headers = target.convert(source);

		Assertions.assertEquals(1, headers.get("Content-Type").size());
		Assertions.assertEquals("application/json", headers.get("Content-Type").get(0));

		Assertions.assertEquals(2, headers.get("scope").size());
		Assertions.assertEquals("15", headers.get("scope").get(0));
		Assertions.assertEquals("twelve!", headers.get("scope").get(1));
	}
}
