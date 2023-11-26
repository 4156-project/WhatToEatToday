package com.whattoeattoday.recommendationservice.user.request;

import lombok.Data;

/**
 * @author Jiarong Shi js6132@columbia.edu
 * @Date 11/26/2023
 */
@Data
public class UserVerifyRequest {
    String username;
    String password;
    String category;
}
