package com.task.poll;


import com.task.poll.model.Role;
import com.task.poll.model.User;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;

import static com.task.poll.model.AbstractBaseEntity.START_SEQ;
import static com.task.poll.model.Role.ROLE_ADMIN;
import static com.task.poll.model.Role.ROLE_USER;

public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 2;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", true, ROLE_USER);
    public static final User USER2 = new User(USER_ID + 1, "User2", "user2@yandex.ru", "password", true, ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "password", true, ROLE_ADMIN, ROLE_USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", true, ROLE_USER);
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(ROLE_ADMIN));
        return updated;
    }

    public static TestMatchers<User> USER_MATCHERS = TestMatchers.useFieldsComparator(User.class, "registered", "password");
}
