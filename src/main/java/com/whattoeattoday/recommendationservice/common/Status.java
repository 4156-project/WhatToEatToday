package com.whattoeattoday.recommendationservice.common;

public enum Status implements StatusCode {

    SUCCESS(200, "Operation Success"),
    PARAM_ERROR(400, "Invalid parameters"),
    NOT_FOUND(404, "Resource not found"),
    FAILURE(500, "Application internal error");

    private final int code;
    private final String message;

    Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public String toString() {
        return String.valueOf(this.code);
    }
}
