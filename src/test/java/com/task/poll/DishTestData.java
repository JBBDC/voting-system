package com.task.poll;

import com.task.poll.model.Dish;
import com.task.poll.to.DishTo;

import java.math.BigDecimal;
import java.util.List;

import static com.task.poll.util.DishUtil.makeTos;

public class DishTestData {
    public static int DISH_1_ID = 100006;
    public static int NOT_EXISTED_ID = 1;

    public static Dish DISH_1 = new Dish(DISH_1_ID, "actual dish one", BigDecimal.valueOf(10));
    public static Dish DISH_2 = new Dish(DISH_1_ID + 1, "old dish one", BigDecimal.valueOf(5.5));
    public static Dish DISH_3 = new Dish(DISH_1_ID + 2, "old dish two", BigDecimal.valueOf(7.3));
    public static Dish DISH_4 = new Dish(DISH_1_ID + 3, "actual dish two", BigDecimal.valueOf(100));
    public static Dish DISH_5 = new Dish(DISH_1_ID + 4, "actual dish three", BigDecimal.valueOf(3.2));
    public static Dish DISH_6 = new Dish(DISH_1_ID + 5, "old dish three", BigDecimal.valueOf(20));
    public static Dish DISH_7 = new Dish(DISH_1_ID + 6, "old and cold", BigDecimal.valueOf(21));

    public static Dish getCreated() {
        return new Dish("new Dish", BigDecimal.valueOf(22));
    }

    public static Dish getUpdated() {
        Dish updated = new Dish(DISH_1);
        updated.setName("updated name");
        return updated;
    }

    public static List<DishTo> REST_1_DISHES = makeTos(DISH_1, DISH_2, DISH_3);
    public static List<DishTo> REST_2_DISHES = makeTos(DISH_4, DISH_5, DISH_6);

    public static TestMatchers<DishTo> DISH_TO_MATCHERS = TestMatchers.useEquals(DishTo.class);
    public static TestMatchers<Dish> DISH_MATCHERS = TestMatchers.useFieldsComparator(Dish.class, "restaurant", "created");
}
