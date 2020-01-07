package com.task.poll.util;

import com.task.poll.model.Dish;
import com.task.poll.model.Restaurant;
import com.task.poll.to.BaseTo;
import com.task.poll.to.DishTo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DishUtil {
    public static List<DishTo> makeTos(Restaurant restaurant) {
        if (restaurant != null && restaurant.getMenu() != null) {
            return restaurant.getMenu().stream()
                    .map(d -> new DishTo(d.getId(), d.getName(), d.getPrice()))
                    .sorted(Comparator.comparingInt(BaseTo::getId).reversed())
                    .collect(Collectors.toList());
        }
        return null;
    }

    public static DishTo makeTo(Dish dish) {
        return new DishTo(dish.getId(), dish.getName(), dish.getPrice());
    }
}
