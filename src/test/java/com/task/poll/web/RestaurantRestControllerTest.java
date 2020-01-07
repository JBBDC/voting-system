package com.task.poll.web;

import com.task.poll.RestaurantTestData;
import com.task.poll.model.Restaurant;
import com.task.poll.repository.RestaurantRepository;
import com.task.poll.util.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.task.poll.RestaurantTestData.*;
import static com.task.poll.TestUtil.readFromJson;
import static com.task.poll.UserTestData.*;
import static com.task.poll.util.RestaurantUtil.makeTo;
import static com.task.poll.util.RestaurantUtil.makeTos;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class RestaurantRestControllerTest extends AbstractControllerTest {

    @Autowired
    RestaurantRepository repository;

    public RestaurantRestControllerTest() {
        super(RestaurantRestController.REST_URL);
    }

    @Test
    void getAllWithMenu() throws Exception {
        perform(doGet().basicAuth(USER))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(RESTS_TO_MATCHERS.contentJson(makeTos(List.of(REST_1_ACTUAL_DISHES, REST_2_ACTUAL_DISHES), USER_ID)));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(doGet())
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getOne() throws Exception {
        perform(doGet(REST_1_ID).basicAuth(USER))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(RESTS_TO_MATCHERS.contentJson(makeTo(REST_1, USER_ID)));
    }

    @Test
    void getOneNotFound() throws Exception {
        perform(doGet(REST_1_ID + 100).basicAuth(USER))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createWithLocation() throws Exception {
        Restaurant newRest = RestaurantTestData.getCreated();
        ResultActions action = perform(doPost().jsonBody(newRest).basicAuth(ADMIN));

        Restaurant created = readFromJson(action, Restaurant.class);
        Integer newId = created.getId();
        newRest.setId(newId);
        RESTS_MATCHERS.assertMatch(created, newRest);
        RESTS_MATCHERS.assertMatch(repository.get(newId), newRest);
    }

    @Test
    void update() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        perform(doPut(REST_1_ID).jsonBody(updated).basicAuth(ADMIN))
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
        perform(doPut(REST_1_ID + 100).jsonBody(updated).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void delete() throws Exception {
        perform(doDelete(REST_1_ID).basicAuth(ADMIN))
                .andExpect(status().isNoContent());
        Assertions.assertThrows(NotFoundException.class, () -> repository.get(REST_1_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(doDelete(REST_1_ID + 100).basicAuth(ADMIN))
                .andExpect(status().isUnprocessableEntity());
    }
}