package com.whattoeattoday.recommendationservice.user.utils;


import com.whattoeattoday.recommendationservice.user.model.User;

import java.util.Map;

/**
 * @author Jiarong Shi js6132@columbia.edu
 * @Date 11/24/2023
 */

public class UserAdapter {
    public static User adaptQueryData(Map<String,Object> data) {
        User user = new User();
        user.setUsername(String.valueOf(data.get("username")));
        user.setEncodedPassword(String.valueOf(data.get("password")));
        user.setEmail(String.valueOf(data.get("email")));
        String category = String.valueOf(data.get("category"));
        user.setCategory(category);
        return user;
    }
}
