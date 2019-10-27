package com.jonathanrobertson.restflows.commands;

import com.jonathanrobertson.restflows.enums.CommandContext;

import com.jonathanrobertson.restflows.models.Place;
import com.jonathanrobertson.restflows.services.MemoryService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.util.UriComponentsBuilder;

@ShellComponent
public class ManagementCommands {
    private final MemoryService mem;
    public ManagementCommands(MemoryService mem) {
        this.mem = mem;
    }

    @ShellMethod("show values in memory; can filter on context")
    public String show(CommandContext context) {
        switch(context) {
            case VAR:
                return mem.vars.toString();
            case PLACE:
                return mem.places.toString();
            case FLOW:
                return "not ready yet";
            default:
                return mem.toString();
        }
    }

    @ShellMethod("set a value in memory")
    public String set(CommandContext context, String name, String value) {
        switch(context) {
            case VAR:
                mem.vars.put(name, value);
                break;
            case PLACE:
                mem.places.put(name, new Place(value));
                break;
            case FLOW:
                return "not ready yet";
            default:
                return "must provide a context";
        }
        return "[+] " + name + "=" + value;
    }

    @ShellMethod("remove a value from memory")
    public String rem(CommandContext context, String name) {
        switch(context) {
            case VAR:
                mem.vars.remove(name);
                break;
            case PLACE:
                mem.places.remove(name);
            case FLOW:
                return "not ready yet";
            default:
                return "must provide a context";
        }
        return "[-] " + name;
    }

    @ShellMethod("test place template")
    public String test(String name) {
        return UriComponentsBuilder.fromUriString(mem.places.get(name).getUriTemplate()).build(mem.vars).toString();
    }
}
