package com.whattoeattoday.recommendationservice.user.utils;


import com.whattoeattoday.recommendationservice.user.model.User;

import java.util.ArrayList;
import java.util.List;
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
        String collectionString = String.valueOf(data.get("collection"));
        List<Integer> collection = new ArrayList<>();
        String[] parts = collectionString.split(",");
        for (String s : parts) {
            if (s != null && s.length() > 0) {
                collection.add(Integer.valueOf(s));
            }
        }
        user.setCollection(collection);
        return user;
    }

    public static void main(String[] args) {
        String s = "1";
        String[] parts = s.split(",");
        System.out.println(parts);
    }
}
