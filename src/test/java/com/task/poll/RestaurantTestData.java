package com.task.poll;

import com.task.poll.model.Restaurant;
import com.task.poll.to.RestaurantTo;

import java.util.List;

import static com.task.poll.DishTestData.*;

public class RestaurantTestData {
    public static int REST_1_ID = 100003;

    public static Restaurant REST_1_ACTUAL_DISHES = new Restaurant(REST_1_ID, "First", List.of(DISH_1));
    public static Restaurant REST_1 = new Restaurant(REST_1_ID, "First", List.of(DISH_1, DISH_2, DISH_3));
    public static Restaurant REST_2_ACTUAL_DISHES = new Restaurant(REST_1_ID + 1, "Second", List.of(DISH_4, DISH_5));
    public static Restaurant REST_2 = new Restaurant(REST_1_ID + 1, "Second", List.of(DISH_4, DISH_5, DISH_6));
    public static Restaurant REST_3 = new Restaurant(REST_1_ID + 2, "Diner without dishes", List.of(DISH_7));

    public static Restaurant getCreated() {
        return new Restaurant("Gabagool");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(REST_1_ID, "Updated one");
    }

    public static TestMatchers<RestaurantTo> RESTS_TO_MATCHERS = TestMatchers.useEquals(RestaurantTo.class);
    public static TestMatchers<Restaurant> RESTS_MATCHERS = TestMatchers.useFieldsComparator(Restaurant.class, "votes", "menu");
}
