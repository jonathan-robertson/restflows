package com.jonathanrobertson.restflows.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jonathanrobertson.restflows.models.Place;
import com.jonathanrobertson.restflows.services.MemoryService;
import com.jonathanrobertson.restflows.services.PathfinderService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.util.UriComponentsBuilder;

@ShellComponent
public class ManagementCommands {
    private final MemoryService mem;
    private final ObjectMapper mapper;
    private final PathfinderService pathfinder;

    public ManagementCommands(MemoryService mem, ObjectMapper mapper, PathfinderService pathfinder) {
        this.mem = mem;
        this.mapper = mapper;
        this.pathfinder = pathfinder;
    }

    @ShellMethod("show values from memory")
    public String show(@ShellOption(value = {"-f", "--filter"}, defaultValue = "") String filter, @ShellOption(value = {"-p", "--pretty"}, defaultValue = "false") boolean pretty) throws JsonProcessingException {
        ObjectWriter w = pretty ? mapper.writerWithDefaultPrettyPrinter() : mapper.writer();
        return w.writeValueAsString(filter.length() > 0 ? pathfinder.follow(mapper.valueToTree(mem), filter) : mem);
    }

    @ShellMethod("make a variable")
    public String mkv(String name, String value) {
        mem.vars.put(name, value);
        return "[+] " + name + "=" + value;
    }

    @ShellMethod("make a place")
    public String mkp(String name, String value) {
        mem.places.put(name, new Place(value));
        return "[+] " + name + "=" + value;
    }

    @ShellMethod("make a flow")
    public String mkf(String name, String value) {
        return "not ready yet";
    }

    @ShellMethod("remove a variable")
    public String rmv(String name) {
        mem.vars.remove(name);
        return "[-] " + name;
    }

    @ShellMethod("remove a place")
    public String rmp(String name) {
        mem.places.remove(name);
        return "[-] " + name;
    }

    @ShellMethod("remove a flow")
    public String rmf(String name) {
        return "not ready yet";
    }

    @ShellMethod("test place template")
    public String test(String name) {
        return UriComponentsBuilder.fromUriString(mem.places.get(name).getUriTemplate()).build(mem.vars).toString();
    }
}
