package com.task.poll.web;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/votes")
public class VoteRestController {

    @GetMapping
    public List<String> getAll() {
        return null;
    }
}
