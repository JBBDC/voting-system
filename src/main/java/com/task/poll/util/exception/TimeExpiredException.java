package com.task.poll.util.exception;

import java.util.Arrays;

public class TimeExpiredException extends RuntimeException {

    public TimeExpiredException(String... args) {
        super(Arrays.toString(args));
    }

}
