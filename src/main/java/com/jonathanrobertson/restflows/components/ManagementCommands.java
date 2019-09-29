package com.jonathanrobertson.restflows.components;

import java.util.HashMap;
import java.util.Map;

import com.jonathanrobertson.restflows.enums.CommandContest;
import com.jonathanrobertson.restflows.services.PlacesService;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ManagementCommands {
    public final PlacesService places;

    public ManagementCommands(PlacesService places) {
        this.places = places;
    }

    @ShellMethod("record an object to system memory.")
    public String add(CommandContest context) {
        // switch (CommandContest.valueOf(context.toUpperCase())) {
        switch (context) {
        case PLACE:
            return "recognized context as place";
        // break;
        default:
            return "invalid context; try using " + CommandContest.values();
        }
    }

    private static final Map<String, String> map;
    static {
        map = new HashMap<>();
        map.put("key1", "one");
        map.put("key2", "two");
    }

    @ShellMethod("map test")
    public String map(String key) {
        return map.get(key);
    }
}
