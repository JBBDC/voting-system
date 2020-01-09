package com.task.poll.web;

import com.task.poll.model.Dish;
import com.task.poll.repository.CrudRestaurantRepository;
import com.task.poll.repository.DishRepository;
import com.task.poll.repository.RestaurantRepository;
import com.task.poll.to.DishTo;
import com.task.poll.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.task.poll.util.DishUtil.makeTo;
import static com.task.poll.util.DishUtil.makeTos;
import static com.task.poll.util.ValidationUtil.assureIdConsistent;
import static com.task.poll.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(com.task.poll.web.DishRestController.REST_URL)
public class DishRestController {
    static final String REST_URL = "/rest/dishes";

    final CrudRestaurantRepository restaurantRepository;

    final DishRepository dishRepository;

    @Autowired
    public DishRestController(CrudRestaurantRepository restaurantRepository, DishRepository dishRepository) {
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public DishTo get(@PathVariable int id) {
        return makeTo(dishRepository.get(id));
    }

    @GetMapping(value = "/history/{restaurantId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<DishTo> getAll(@PathVariable int restaurantId) {
        return makeTos(dishRepository.getAll(restaurantId));
    }

    @Transactional
    @PostMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DishTo> createWithLocation(@RequestBody Dish dish, @PathVariable int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        checkNew(dish);
        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        Dish created = dishRepository.save(dish);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(makeTo(created));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Dish dish, @PathVariable int id) {
        Assert.notNull(dish, "restaurant must not be null");
        assureIdConsistent(dish, id);
        dishRepository.save(dish);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        if (!dishRepository.delete(id)) {
            throw new NotFoundException("id = " + id);
        }
    }
}
