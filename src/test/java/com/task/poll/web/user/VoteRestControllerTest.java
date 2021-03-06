package com.task.poll.web.user;

import com.task.poll.RestaurantTestData;
import com.task.poll.VoteTestData;
import com.task.poll.model.Vote;
import com.task.poll.service.VoteService;
import com.task.poll.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.*;
import java.time.temporal.ChronoUnit;

import static com.task.poll.DishTestData.*;
import static com.task.poll.UserTestData.*;
import static com.task.poll.VoteTestData.*;
import static com.task.poll.util.VoteUtil.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteRestControllerTest extends AbstractControllerTest {

    @Autowired
    VoteService repository;

    public VoteRestControllerTest() {
        super(VoteRestController.REST_URL);
    }

    @Test
    void getToday() throws Exception {
        perform(doGet().basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(VOTE_TO_MATCHERS.contentJson(makeTo(ADMIN_VOTE_2)));
    }

    @Test
    void vote() throws Exception {
        perform(doPost("?restaurantId={restaurantId}", RestaurantTestData.REST_1_ID).basicAuth(USER))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(VOTE_TO_MATCHERS.contentJson(makeTo(VoteTestData.getCreated())));
    }

    @Test
    void voteForNotExisted() throws Exception {
        perform(doPost("?restaurantId={restaurantId}", NOT_EXISTED_ID).basicAuth(USER2))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    void revoteBeforeExpired() throws Exception {
        Vote updated = VoteTestData.getUpdated();
        VoteRestController.EXPIRED = LocalTime.now().plus(5, ChronoUnit.MINUTES);
        perform(doPost("?restaurantId={restaurantId}", RestaurantTestData.REST_1_ID + 1).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(VOTE_TO_MATCHERS.contentJson(makeTo(updated)));
        VOTE_TO_MATCHERS.assertMatch(makeTos(repository.getAllByUser(ADMIN_ID)), makeTos(ADMIN_VOTE_1, updated));
    }

    @Test
    void revoteAfterExpired() throws Exception {
        VoteRestController.EXPIRED = LocalTime.now().minus(5, ChronoUnit.MINUTES);
        perform(doPost("?restaurantId={restaurantId}", RestaurantTestData.REST_1_ID + 1).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isConflict());
        VOTE_TO_MATCHERS.assertMatch(makeTos(repository.getAllByUser(ADMIN_ID)), makeTos(ADMIN_VOTE_1, ADMIN_VOTE_2));
    }
}