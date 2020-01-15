package com.task.poll.web.admin;

import com.task.poll.RestaurantTestData;
import com.task.poll.model.Restaurant;
import com.task.poll.repository.RestaurantRepository;
import com.task.poll.to.RestaurantTo;
import com.task.poll.util.exception.NotFoundException;
import com.task.poll.web.AbstractControllerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static com.task.poll.DishTestData.*;
import static com.task.poll.RestaurantTestData.*;
import static com.task.poll.TestUtil.readFromJson;
import static com.task.poll.UserTestData.*;
import static com.task.poll.util.RestaurantUtil.makeTo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminRestaurantRestControllerTest extends AbstractControllerTest {

    @Autowired
    RestaurantRepository repository;

    public AdminRestaurantRestControllerTest() {
        super(AdminRestaurantRestController.REST_URL);
    }

    @Test
    void getAll() throws Exception {
        perform(doGet().basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(RESTS_MATCHERS.contentJson(REST_3, REST_1, REST_2));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(doGet())
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void get() throws Exception {
        perform(doGet(REST_1_ID).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(RESTS_TO_MATCHERS.contentJson(makeTo(REST_1)));
    }

    @Test
    void getByName() throws Exception {
        perform(doGet("/by?name=Diner without dishes").basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(RESTS_TO_MATCHERS.contentJson(makeTo(REST_3)));
    }

    @Test
    void getNotFound() throws Exception {
        perform(doGet(NOT_EXISTED_ID).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createWithLocation() throws Exception {
        Restaurant newRest = RestaurantTestData.getCreated();
        ResultActions action = perform(doPost().jsonBody(newRest).basicAuth(ADMIN))
                .andDo(print());

        RestaurantTo created = readFromJson(action, RestaurantTo.class);
        Integer newId = created.getId();
        newRest.setId(newId);
        RESTS_TO_MATCHERS.assertMatch(created, makeTo(newRest));
        RESTS_MATCHERS.assertMatch(repository.get(newId), newRest);
    }

    @Test
    void createEmptyName() throws Exception {
        Restaurant newRest = new Restaurant("");
        perform(doPost().jsonBody(newRest).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        perform(doPut(REST_1_ID).jsonBody(updated).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isNoContent());
        RESTS_MATCHERS.assertMatch(repository.get(REST_1_ID), updated);
    }

    @Test
    void updateWithoutAuthority() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        perform(doPut(REST_1_ID).jsonBody(updated).basicAuth(USER))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void updateNotFound() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        perform(doPut(NOT_EXISTED_ID).jsonBody(updated).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void delete() throws Exception {
        perform(doDelete(REST_1_ID).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertThrows(NotFoundException.class, () -> repository.get(REST_1_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(doDelete(NOT_EXISTED_ID).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}