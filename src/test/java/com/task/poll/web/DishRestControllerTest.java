package com.task.poll.web;

import com.task.poll.DishTestData;
import com.task.poll.TestUtil;
import com.task.poll.model.Dish;
import com.task.poll.repository.DishRepository;
import com.task.poll.util.DishUtil;
import com.task.poll.util.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static com.task.poll.DishTestData.*;
import static com.task.poll.RestaurantTestData.*;
import static com.task.poll.UserTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class DishRestControllerTest extends AbstractControllerTest {

    @Autowired
    DishRepository repository;

    public DishRestControllerTest() {
        super(DishRestController.REST_URL);
    }

    @Test
    void get() throws Exception {
        perform(doGet(DISH_1_ID).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(DISH_TO_MATCHERS.contentJson(DishUtil.makeTo(DISH_1)));
    }

    @Test
    void getNotFound() throws Exception {
        perform(doGet(NOT_EXISTED_ID).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    void createWithLocation() throws Exception {
        Dish newDish = DishTestData.getCreated();
        ResultActions action = perform(doPost(REST_1_ID).jsonBody(newDish).basicAuth(ADMIN))
                .andDo(print());
        Dish created = TestUtil.readFromJson(action, Dish.class);
        int newId = created.getId();
        newDish.setId(newId);
        DISH_MATCHERS.assertMatch(created, newDish);
        DISH_MATCHERS.assertMatch(repository.get(newId), newDish);

    }

    @Test
    void update() throws Exception {
        Dish updated = DishTestData.getUpdated();
        perform(doPut(DISH_1_ID).jsonBody(updated).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isNoContent());
        DISH_MATCHERS.assertMatch(repository.get(DISH_1_ID), updated);
    }

    @Test
    void updateNotFound() throws Exception {
        Dish updated = DishTestData.getUpdated();
        perform(doPut(NOT_EXISTED_ID).jsonBody(updated).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    void delete() throws Exception {
        perform(doDelete(DISH_1_ID).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertThrows(NotFoundException.class, () -> repository.get(DISH_1_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(doDelete(NOT_EXISTED_ID).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deleteWithoutAuthority() throws Exception {
        perform(doDelete(DISH_1_ID).basicAuth(USER))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}