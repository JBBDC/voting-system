package com.task.poll;

import com.task.poll.model.Dish;

import java.math.BigDecimal;

import static com.task.poll.RestaurantTestData.*;

public class DishTestData {
    public static int DISH_1_ID = 100006;

    public static Dish DISH_1 = new Dish(DISH_1_ID, "actual dish one", BigDecimal.valueOf(10), REST_1);
    public static Dish DISH_2 = new Dish(DISH_1_ID + 1, "old dish one", BigDecimal.valueOf(5.5), REST_1);
    public static Dish DISH_3 = new Dish(DISH_1_ID + 2, "old dish two", BigDecimal.valueOf(7.3), REST_1);
    public static Dish DISH_4 = new Dish(DISH_1_ID + 3, "actual dish two", BigDecimal.valueOf(100), REST_2);
    public static Dish DISH_5 = new Dish(DISH_1_ID + 4, "actual dish three", BigDecimal.valueOf(3.2), REST_2);
    public static Dish DISH_6 = new Dish(DISH_1_ID + 5, "old dish three", BigDecimal.valueOf(20), REST_3);
    public static Dish DISH_7 = new Dish(DISH_1_ID + 6, "old and cold", BigDecimal.valueOf(21), REST_1);

    public static Dish getCreated() {
        return new Dish("new Dish", BigDecimal.valueOf(22), REST_1);
    }
}
