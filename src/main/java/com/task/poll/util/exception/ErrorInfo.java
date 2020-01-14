package com.task.poll.util.exception;

import java.util.HashMap;

public class ErrorInfo {
    private final HashMap<String, Object> error = new HashMap<>();
    private final String url;
    private final String[] details;

    public ErrorInfo(CharSequence url, String... details) {
        this.url = url.toString();
        this.details = details;
        error.put("url", url);
        error.put("details", details);
    }

    public HashMap<String, Object> getError() {
        return error;
    }
}