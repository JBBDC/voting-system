package com.task.poll.web.admin;

import com.task.poll.DishTestData;
import com.task.poll.TestUtil;
import com.task.poll.model.Dish;
import com.task.poll.service.DishService;
import com.task.poll.to.DishTo;
import com.task.poll.util.exception.NotFoundException;
import com.task.poll.web.AbstractControllerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.List;

import static com.task.poll.DishTestData.*;
import static com.task.poll.RestaurantTestData.*;
import static com.task.poll.UserTestData.*;
import static com.task.poll.util.DishUtil.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminDishRestControllerTest extends AbstractControllerTest {

    @Autowired
    DishService repository;

    public AdminDishRestControllerTest() {
        super(AdminDishRestController.REST_URL);
    }

    @Test
    void get() throws Exception {
        perform(doGet("/{restaurantId}/dishes/{id}", REST_1_ID, DISH_1_ID).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(DISH_TO_MATCHERS.contentJson(makeTo(DISH_1)));
    }

    @Test
    void getNotFound() throws Exception {
        perform(doGet("/{restaurantId}/dishes/{id}", REST_1_ID, NOT_EXISTED_ID).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getAll() throws Exception {
        perform(doGet("/{restaurantId}/dishes", REST_1_ID).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(DISH_TO_MATCHERS.contentJson(REST_1_DISHES));
    }

    @Test
    void getBetweenDates() throws Exception {
        perform(doGet("/{restaurantId}/dishes?startDate=2019-11-21&endDate=2019-11-23", REST_1_ID).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(DISH_TO_MATCHERS.contentJson(List.of(makeTo(DISH_3))));
    }

    @Test
    void createWithLocation() throws Exception {
        Dish newDish = DishTestData.getCreated();
        ResultActions action = perform(doPost("/{restaurantId}/dishes", REST_1_ID).jsonBody(newDish).basicAuth(ADMIN))
                .andDo(print());
        DishTo created = TestUtil.readFromJson(action, DishTo.class);
        int newId = created.getId();
        newDish.setId(newId);
        DISH_TO_MATCHERS.assertMatch(created, makeTo(newDish));
        DISH_MATCHERS.assertMatch(repository.get(REST_1_ID, newId), newDish);
    }

    @Test
    void createEmptyName() throws Exception {
        Dish created = new Dish("", BigDecimal.valueOf(10), REST_1);
        perform(doPost("/{restaurantId}/dishes", REST_1_ID).jsonBody(created).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createNegativePrice() throws Exception {
        Dish created = new Dish("Whoops", BigDecimal.valueOf(-10), REST_1);
        perform(doPost("/{restaurantId}/dishes", REST_1_ID).jsonBody(created).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        Dish updated = DishTestData.getUpdated();
        perform(doPut("/{restaurantId}/dishes/{id}", REST_1_ID, DISH_1_ID).jsonBody(updated).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isNoContent());
        DISH_MATCHERS.assertMatch(repository.get(REST_1_ID, DISH_1_ID), updated);
    }

    @Test
    void updateNotFound() throws Exception {
        Dish updated = DishTestData.getUpdated();
        perform(doPut("/{restaurantId}/dishes/{id}", REST_1_ID, NOT_EXISTED_ID).jsonBody(updated).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    void delete() throws Exception {
        perform(doDelete("/{restaurantId}/dishes/{id}", REST_1_ID, DISH_1_ID).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertThrows(NotFoundException.class, () -> repository.get(REST_1_ID, DISH_1_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(doDelete("/{restaurantId}/dishes/{id}", REST_1_ID, NOT_EXISTED_ID).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deleteWithoutAuthority() throws Exception {
        perform(doDelete("/{restaurantId}/dishes/{id}", REST_1_ID, DISH_1_ID).basicAuth(USER))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteWrongRestaurantId() throws Exception {
        perform(doDelete("/{restaurantId}/dishes/{id}", REST_1_ID + 1, DISH_1_ID).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

}