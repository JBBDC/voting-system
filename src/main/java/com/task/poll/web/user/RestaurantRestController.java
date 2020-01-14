package com.task.poll.web.user;

import com.task.poll.repository.RestaurantRepository;
import com.task.poll.to.RestaurantTo;
import com.task.poll.util.RestaurantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(RestaurantRestController.REST_URL)
public class RestaurantRestController {

    static final String REST_URL = "/api/v1/restaurants";

    final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantRestController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RestaurantTo> getAllTodayWithMenu() {
        return RestaurantUtil.makeTos(restaurantRepository.getAllToday());
    }
}
