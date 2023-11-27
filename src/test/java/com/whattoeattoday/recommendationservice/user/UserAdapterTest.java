package com.whattoeattoday.recommendationservice.user;

import com.whattoeattoday.recommendationservice.RecommendationServiceApplication;
import com.whattoeattoday.recommendationservice.user.model.User;
import com.whattoeattoday.recommendationservice.user.utils.MD5Util;
import com.whattoeattoday.recommendationservice.user.utils.UserAdapter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecommendationServiceApplication.class)
public class UserAdapterTest {

    @Test
    public void adaptQueryDataTest() {
        Map<String,Object> data = new HashMap<>();
        data.put("username", "Larry");
        String password = "12345";
        data.put("password", MD5Util.formPassToDBPass(password, "1a2b3c"));
        data.put("email", "js6132@columbia.edu");
        data.put("category", "food");
        User user = UserAdapter.adaptQueryData(data);
        System.out.println(user);
        Assert.assertEquals(user.getUsername(), "Larry");
    }
}
