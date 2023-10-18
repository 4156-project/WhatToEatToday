package com.whattoeattoday.recommendationservice.common;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

/**
 * @author huanglijie
 */
public interface StatusCode extends Serializable {
    /**
     * Status Code
     * @return 200, 400, etc.
     */
    @JsonValue
    int code();

    /**
     * message
     * @return "Operation Success", "Invalid parameters", etc.
     */
    String message();
}
