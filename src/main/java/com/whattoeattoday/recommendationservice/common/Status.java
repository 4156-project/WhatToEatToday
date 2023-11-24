package com.whattoeattoday.recommendationservice.common;

/**
 * Enum Types of Status Code
 * @author huanglijie
 */
public enum Status implements StatusCode {

    /**
     * Operation Success
     */
    SUCCESS(200, "Operation Success"),

    /**
     * Invalid parameters
     */
    PARAM_ERROR(400, "Invalid parameters"),

    /**
     * Data duplicate error
     */
    DUPLICATE_ERROR(400, "Data Duplicate Error"),

    /**
     * Resource not found
     */
    NOT_FOUND(404, "Resource not found"),

    /**
     * Application internal error
     */
    FAILURE(500, "Application internal error"),

    /**
     * Database SQL execution error
     */
    DATABASE_ERROR(500, "Database Execution Error");

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
