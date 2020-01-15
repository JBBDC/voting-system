package com.task.poll.web.user;

import com.task.poll.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static com.task.poll.RestaurantTestData.*;
import static com.task.poll.UserTestData.USER;
import static com.task.poll.util.RestaurantUtil.makeTos;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class RestaurantRestControllerTest extends AbstractControllerTest {

    public RestaurantRestControllerTest() {
        super(RestaurantRestController.REST_URL);
    }

    @Test
    void getAllWithMenu() throws Exception {
        perform(doGet().basicAuth(USER))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(RESTS_TO_MATCHERS.contentJson(makeTos(List.of(REST_1_ACTUAL_DISHES, REST_2_ACTUAL_DISHES))));
    }
}