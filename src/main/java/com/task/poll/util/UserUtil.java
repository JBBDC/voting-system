package com.task.poll.util;

import com.task.poll.model.Role;
import com.task.poll.model.User;
import com.task.poll.to.UserTo;

public class UserUtil {

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }
}