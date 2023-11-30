package com.whattoeattoday.recommendationservice.user.model;

import lombok.Data;

import java.util.Set;

/**
 * @author Jiarong Shi js6132@columbia.edu
 * @Date 11/24/2023
 */
@Data
public class User {
    String id;
    String username;
    String encodedPassword;
    String email;
    String category;
    Set<Long> collection;
}
