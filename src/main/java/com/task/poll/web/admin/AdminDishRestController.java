package com.task.poll.web.admin;

import com.task.poll.model.Dish;
import com.task.poll.repository.CrudRestaurantRepository;
import com.task.poll.repository.DishRepository;
import com.task.poll.to.DishTo;
import com.task.poll.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.task.poll.util.DishUtil.makeTo;
import static com.task.poll.util.DishUtil.makeTos;
import static com.task.poll.util.ValidationUtil.*;

@RestController
@RequestMapping(AdminDishRestController.REST_URL)
public class AdminDishRestController {
    static final String REST_URL = "/api/v1/admin/restaurants";

    final CrudRestaurantRepository restaurantRepository;

    final DishRepository dishRepository;

    @Autowired
    public AdminDishRestController(CrudRestaurantRepository restaurantRepository, DishRepository dishRepository) {
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
    }

    @GetMapping(value = "/{restaurantId}/dishes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public DishTo get(@PathVariable int restaurantId, @PathVariable int id) {
        return makeTo(dishRepository.get(restaurantId, id));
    }

    @GetMapping(value = "/{restaurantId}/dishes", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<DishTo> getBetweenDates(@PathVariable int restaurantId,
                                        @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                        @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return makeTos(dishRepository.getByRestaurantBetweenDates(restaurantId, startDate, endDate));
    }

    @Transactional
    @PostMapping(value = "/{restaurantId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DishTo> createWithLocation(@PathVariable int restaurantId, @RequestBody @Valid Dish dish) {
        checkNew(dish);
        Dish created = dishRepository.save(restaurantId, dish);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{restaurantId}/dishes/{id}")
                .buildAndExpand(created.getRestaurant().getId(), created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(makeTo(created));
    }

    @PutMapping(value = "/{restaurantId}/dishes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restaurantId, @RequestBody @Valid Dish dish, @PathVariable int id) {
        checkId(dish, id);
        dishRepository.save(restaurantId, dish);
    }

    @DeleteMapping("/{restaurantId}/dishes/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        if (!dishRepository.delete(restaurantId, id)) {
            throw new NotFoundException("not found dish with id = " + id + " for restaurant " + restaurantId);
        }
    }
}
