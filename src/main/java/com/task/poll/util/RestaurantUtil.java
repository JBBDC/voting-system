package com.task.poll.util;

import com.task.poll.model.Restaurant;
import com.task.poll.to.RestaurantTo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantUtil {

    public static List<RestaurantTo> makeTos(Restaurant... restaurants) {
        return makeTos(List.of(restaurants));
    }

    public static List<RestaurantTo> makeTos(List<Restaurant> restaurants) {
        if(restaurants != null) {
            return restaurants.stream()
                    .map(RestaurantUtil::makeTo)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public static RestaurantTo makeTo(Restaurant restaurant) {
        if (restaurant != null && !restaurant.isNew()) {
            int id = restaurant.getId();
            return new RestaurantTo(id, restaurant.getName(), DishUtil.makeTos(restaurant));
        }
        return null;
    }
}
