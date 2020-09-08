package com.jonathanrobertson.restflows.commands;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jonathanrobertson.restflows.models.Place;
import com.jonathanrobertson.restflows.services.MemoryService;

@Slf4j
@ShellComponent
@AllArgsConstructor
public class ManagementCommands {
	private final MemoryService mem;

	@ShellMethod("show values from memory")
	public String show(@ShellOption(value = { "-f", "--filter" }, defaultValue = "") String filter, @ShellOption(value = { "-p", "--pretty" }, defaultValue = "false") boolean pretty) throws JsonProcessingException {
		return mem.showMemory(filter, pretty);
	}

	@ShellMethod("make a variable")
	public String mkv(String name, String value) {
		mem.getVars().put(name, value);
		return "[+] " + name + "=" + value;
	}

	@ShellMethod("make a place")
	public String mkp(String name, String value) {
		mem.getPlaces().put(name, new Place(value));
		return "[+] " + name + "=" + value;
	}

	@ShellMethod("make a flow")
	public String mkf(String name, String value) {
		return "not ready yet";
	}

	@ShellMethod("remove a variable")
	public String rmv(String name) {
		mem.getVars().remove(name);
		return "[-] " + name;
	}

	@ShellMethod("remove a place")
	public String rmp(String name) {
		mem.getPlaces().remove(name);
		return "[-] " + name;
	}

	@ShellMethod("remove a flow")
	public String rmf(String name) {
		return "not ready yet";
	}

	@ShellMethod("validate a place template")
	public String validate(String place) {
		return mem.validatePlace(place);
	}

	@ShellMethod("save memory to file")
	public String save() {
		mem.save();
		return "[+] successfully saved memory; it will load automatically on next launch";
	}
}
