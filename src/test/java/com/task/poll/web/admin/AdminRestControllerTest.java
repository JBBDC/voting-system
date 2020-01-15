package com.task.poll.web.admin;

import com.task.poll.TestUtil;
import com.task.poll.UserTestData;
import com.task.poll.model.Role;
import com.task.poll.model.User;
import com.task.poll.repository.UserRepository;
import com.task.poll.util.exception.NotFoundException;
import com.task.poll.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static com.task.poll.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminRestControllerTest extends AbstractControllerTest {

    @Autowired
    private UserRepository userService;

    AdminRestControllerTest() {
        super(AdminRestController.REST_URL);
    }

    @Test
    void get() throws Exception {
        perform(doGet(ADMIN_ID).basicAuth(ADMIN))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHERS.contentJson(ADMIN));
    }

    @Test
    void getNotFound() throws Exception {
        perform(doGet(1).basicAuth(ADMIN))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void getByEmail() throws Exception {
        perform(doGet("by?email={email}", ADMIN.getEmail()).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHERS.contentJson(ADMIN));
    }

    @Test
    void delete() throws Exception {
        perform(doDelete(USER_ID).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> userService.get(USER_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(doDelete(1).basicAuth(ADMIN))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(doGet())
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getForbidden() throws Exception {
        perform(doGet().basicAuth(USER))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void update() throws Exception {
        User updated = UserTestData.getUpdated();
        perform(doPut(USER_ID).jsonBody(updated).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isNoContent());

        USER_MATCHERS.assertMatch(userService.get(USER_ID), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        User newUser = UserTestData.getNew();
        ResultActions action = perform(doPost().jsonBody(newUser).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isCreated());

        User created = TestUtil.readFromJson(action, User.class);
        Integer newId = created.getId();
        newUser.setId(newId);
        USER_MATCHERS.assertMatch(created, newUser);
        USER_MATCHERS.assertMatch(userService.get(newId), newUser);
    }

    @Test
    void getAll() throws Exception {
        perform(doGet().basicAuth(ADMIN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHERS.contentJson(ADMIN, USER, USER2));
    }


    @Test
    void createInvalid() throws Exception {
        User expected = new User(null, "name", "", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        perform(doPost().jsonBody(expected).basicAuth(ADMIN))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void updateInvalid() throws Exception {
        User updated = new User(USER);
        updated.setName("");
        perform(doPut(USER_ID).jsonBody(updated).basicAuth(ADMIN))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }
}