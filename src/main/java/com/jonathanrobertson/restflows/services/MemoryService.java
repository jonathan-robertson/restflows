package com.jonathanrobertson.restflows.services;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jonathanrobertson.restflows.models.Memory;
import com.jonathanrobertson.restflows.models.Place;

@Slf4j
@Service
public class MemoryService {

	private final ObjectMapper		mapper;
	private final PathfinderService	pathfinder;
	private final String			filename;

	private final Memory memory;

	public MemoryService(ObjectMapper mapper, PathfinderService pathfinder, @Value("${restflows.memory-service.filename}") String filename) {
		this.mapper = mapper;
		this.pathfinder = pathfinder;
		this.filename = filename;

		this.memory = load();
	}

	/**
	 * Get a reference to our Vars map
	 * 
	 * @return {@code Map<String, String>}
	 */
	public Map<String, String> getVars() {
		return memory.getVars();
	}

	/**
	 * Get a reference to our Places map
	 * 
	 * @return {@code Map<String, Place>}}
	 */
	public Map<String, Place> getPlaces() {
		return memory.getPlaces();
	}

	/**
	 * Validate place template against current var collection
	 * 
	 * @param  place to validate
	 * @return       {@link String}
	 */
	public String validatePlace(String place) {
		return UriComponentsBuilder.fromUriString(memory.getPlaces().get(place).getUriTemplate()).build(memory.getVars()).toString();
	}

	/**
	 * Save memory to a local file which will be loaded on next launch
	 * 
	 * @throws IOException if unable to write memory to file
	 */
	public void save() {
		try (FileWriter w = new FileWriter(filename)) {
			w.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.memory));
		} catch (IOException e) {
			log.error("unable to save to file", e);
			throw new RuntimeException(e);
		}
	}

	private Memory load() {
		try {
			FileReader r = new FileReader(filename);
			return mapper.readValue(r, Memory.class);
		} catch (IOException e) {
			log.warn("unable to load " + filename + " - defaulting to new memory set", e);
			return new Memory();
		}
	}

	public String showMemory(String filter, boolean pretty) {
		ObjectWriter w = pretty ? mapper.writerWithDefaultPrettyPrinter() : mapper.writer();
		try {
			return w.writeValueAsString(filter.length() > 0 ? pathfinder.follow(mapper.valueToTree(memory), filter) : memory);
		} catch (JsonProcessingException e) {
			log.error("failed to process JSON data", e);
			return "failure";
		}
	}
}
