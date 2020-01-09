package com.task.poll.web;

import com.task.poll.RestaurantTestData;
import com.task.poll.VoteTestData;
import com.task.poll.model.Vote;
import com.task.poll.repository.VoteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.List;

import static com.task.poll.RestaurantTestData.RESTS_MATCHERS;
import static com.task.poll.UserTestData.*;
import static com.task.poll.VoteTestData.*;
import static com.task.poll.util.VoteUtil.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class VoteRestControllerTest extends AbstractControllerTest {

    @Autowired
    VoteRepository repository;

    public VoteRestControllerTest() {
        super(VoteRestController.REST_URL);
    }

    @Test
    void getAll() throws Exception {
        perform(doGet().basicAuth(USER))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(VOTE_TO_MATCHERS.contentJson(makeTos(List.of(USER_VOTE_1, USER_VOTE_2))));
    }

    @Test
    void vote() throws Exception {
        perform(doPost(RestaurantTestData.REST_1_ID).basicAuth(USER2))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(VOTE_TO_MATCHERS.contentJson(makeTo(getCreated())));
    }

    @Test
    void revoteBeforeExpired() throws Exception {
        Vote updated = VoteTestData.getUpdated();
        VoteRestController.EXPIRED = LocalTime.now().plus(5, ChronoUnit.MINUTES);
        perform(doPost(RestaurantTestData.REST_1_ID + 1).basicAuth(USER))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(VOTE_TO_MATCHERS.contentJson(makeTo(updated)));
        VOTE_TO_MATCHERS.assertMatch(makeTos(repository.getAll(USER_ID)), makeTos(USER_VOTE_1, updated));
    }

    @Test
    void revoteAfterExpired() throws Exception {
        VoteRestController.EXPIRED = LocalTime.now().minus(5, ChronoUnit.MINUTES);
        perform(doPost(RestaurantTestData.REST_1_ID + 1).basicAuth(USER))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(VOTE_TO_MATCHERS.contentJson(makeTo(USER_VOTE_2)));
        VOTE_TO_MATCHERS.assertMatch(makeTos(repository.getAll(USER_ID)), makeTos(USER_VOTE_1, USER_VOTE_2));
    }
}