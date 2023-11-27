package com.whattoeattoday.recommendationservice.user.model;

import lombok.Data;

/**
 * @author Jiarong Shi js6132@columbia.edu
 * @Date 11/24/2023
 */
@Data
public class User {
    String username;
    String encodedPassword;
    String email;
    String category;
}
