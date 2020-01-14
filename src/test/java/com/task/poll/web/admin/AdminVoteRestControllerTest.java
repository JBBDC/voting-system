package com.task.poll.web.admin;

import com.task.poll.web.AbstractControllerTest;
import com.task.poll.web.admin.AdminVoteRestController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.List;

import static com.task.poll.UserTestData.ADMIN;
import static com.task.poll.VoteTestData.*;
import static com.task.poll.util.VoteUtil.makeTos;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class AdminVoteRestControllerTest extends AbstractControllerTest {

    AdminVoteRestControllerTest() {
        super(AdminVoteRestController.REST_URL);
    }

    @Test
    void getAll() throws Exception {
        perform(doGet().basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(VOTE_TO_MATCHERS.contentJson(makeTos(List.of(USER_VOTE_1, USER_VOTE_2, ADMIN_VOTE_1, ADMIN_VOTE_2))));
    }

    @Test
    void getBetweenDates() throws Exception {
        perform(doGet("?startDate=2020-01-12").basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(VOTE_TO_MATCHERS.contentJson(makeTos(List.of(USER_VOTE_2, ADMIN_VOTE_2))));
    }
}