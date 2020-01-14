package com.task.poll.web.admin;

import com.task.poll.model.User;
import com.task.poll.repository.UserRepository;
import com.task.poll.util.ValidationUtil;
import com.task.poll.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController {

    @Autowired
    UserRepository repository;

    static final String REST_URL = "/api/v1/admin/users";

    @GetMapping
    public List<User> getAll() {
        return repository.getAll();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        return repository.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@Validated @RequestBody User user) {
        Assert.notNull(user, "user must not be null");
        User created = repository.save(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        if (!repository.delete(id)) {
            throw new NotFoundException("not found restaurant with id = " + id);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated @RequestBody User user, @PathVariable int id) {
        Assert.notNull(user, "user must not be null");
        ValidationUtil.assureIdConsistent(user, id);
        repository.save(user);
    }

    @GetMapping("/by")
    public User getByMail(@RequestParam String email) {
        return repository.getByEmail(email);
    }
}