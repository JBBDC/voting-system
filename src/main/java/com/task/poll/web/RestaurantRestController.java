package com.task.poll.web;

import com.task.poll.model.Restaurant;
import com.task.poll.repository.RestaurantRepository;
import com.task.poll.repository.UserRepository;
import com.task.poll.repository.VoteRepository;
import com.task.poll.to.RestaurantTo;
import com.task.poll.util.RestaurantUtil;
import com.task.poll.util.ValidationUtil;
import com.task.poll.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.task.poll.util.RestaurantUtil.*;
import static com.task.poll.util.ValidationUtil.assureIdConsistent;
import static com.task.poll.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(RestaurantRestController.REST_URL)
public class RestaurantRestController {
    static final String REST_URL = "/rest/restaurants";

    final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantRestController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

//    @PostMapping("/{id}")
//    public Vote vote(@PathVariable int id) {
//        User user = userRepository.get(SecurityUtil.getAuthUser());
//        Restaurant restaurant = restaurantRepository.get(id);
//        Vote vote = new Vote(user, LocalDateTime.now(), restaurant);
//        return voteRepository.save(vote);
//    }

    @GetMapping("/{id}")
    public RestaurantTo get(@PathVariable int id) {
        return makeTo(restaurantRepository.get(id));
    }

    @GetMapping
    public List<RestaurantTo> getAllToday() {
        return RestaurantUtil.makeTos(restaurantRepository.getAllToday());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNew(restaurant);
        Restaurant created = restaurantRepository.save(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant restaurant, @PathVariable int id) {
        Assert.notNull(restaurant, "restaurant must not be null");
        assureIdConsistent(restaurant, id);
        restaurantRepository.save(restaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        if (!restaurantRepository.delete(id)) {
            throw new NotFoundException("id = " + id);
        }
    }
}
