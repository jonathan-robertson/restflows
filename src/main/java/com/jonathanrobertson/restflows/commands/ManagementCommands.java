package com.jonathanrobertson.restflows.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jonathanrobertson.restflows.enums.CommandContext;
import com.jonathanrobertson.restflows.models.Place;
import com.jonathanrobertson.restflows.services.MemoryService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.util.UriComponentsBuilder;

@ShellComponent
public class ManagementCommands {
    private final MemoryService mem;
    private final ObjectMapper mapper;

    public ManagementCommands(MemoryService mem, ObjectMapper mapper) {
        this.mem = mem;
        this.mapper = mapper;
    }

    @ShellMethod("show values in memory; can filter on context")
    public String show(@ShellOption(defaultValue = "NONE") CommandContext context, @ShellOption(value = {"-p", "--pretty"}, defaultValue = "false") boolean pretty) throws JsonProcessingException {
        ObjectWriter w = pretty ? mapper.writerWithDefaultPrettyPrinter() : mapper.writer();
        switch (context) {
            case VAR:
                return w.writeValueAsString(mem.vars);
            case PLACE:
                return w.writeValueAsString(mem.places);
            case FLOW:
                return "not ready yet";
            default:
                return w.writeValueAsString(mem);
        }
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
