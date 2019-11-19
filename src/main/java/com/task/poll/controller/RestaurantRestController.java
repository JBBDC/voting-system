package com.task.poll.controller;

import com.task.poll.model.Restaurant;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/poll")
public class RestaurantRestController {

    @GetMapping("/restaurants/{id}")
    public Restaurant get(@PathVariable int id) {
        return null;
    }

    @PostMapping("/restaurants/{id}")
    public Restaurant vote(@PathVariable int id) {
        return null;
    }

    @GetMapping("/restaurants")
    public List<String> getAll() {
        return null;
    }

    @PutMapping("/restaurants")
    public Restaurant create() {
        return null;
    }

    @DeleteMapping("/restaurants")
    public boolean delete() {
        return false;
    }

    @PostMapping("/restaurants/{id}")
    public Restaurant update(@PathVariable int id) {
        return null;
    }

    @PostMapping(value = "/vote", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean vote() {
        return true;
    }

}
