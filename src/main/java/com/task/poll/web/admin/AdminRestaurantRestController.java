package com.task.poll.web.admin;

import com.task.poll.model.Restaurant;
import com.task.poll.repository.RestaurantRepository;
import com.task.poll.to.RestaurantTo;
import com.task.poll.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.task.poll.util.RestaurantUtil.*;
import static com.task.poll.util.ValidationUtil.*;

@RestController
@RequestMapping(AdminRestaurantRestController.REST_URL)
public class AdminRestaurantRestController {
    static final String REST_URL = "/api/v1/admin/restaurants";

    final RestaurantRepository restaurantRepository;

    @Autowired
    public AdminRestaurantRestController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RestaurantTo get(@PathVariable int id) {
        return makeTo(restaurantRepository.get(id));
    }

    @GetMapping("/by")
    @ResponseStatus(HttpStatus.OK)
    public RestaurantTo get(@RequestParam("name") String name) {
        return makeTo(restaurantRepository.getByName(name));
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RestaurantTo> getAll() {
        return (makeTos(restaurantRepository.getAll()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantTo> createWithLocation(@RequestBody @Valid Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNew(restaurant);
        Restaurant created = restaurantRepository.save(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(makeTo(created));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid Restaurant restaurant, @PathVariable int id) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkId(restaurant, id);
        restaurantRepository.save(restaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        if (!restaurantRepository.delete(id)) {
            throw new NotFoundException("not found restaurant with id = " + id);
        }
    }
}
