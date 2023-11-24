package com.whattoeattoday.recommendationservice.user;

import com.whattoeattoday.recommendationservice.RecommendationServiceApplication;
import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.user.request.UserLoginRequest;
import com.whattoeattoday.recommendationservice.user.request.UserRegisterRequest;
import com.whattoeattoday.recommendationservice.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecommendationServiceApplication.class)
public class UserServiceImplTest {

    @Resource
    private UserServiceImpl userService;

    @Test
    public void userRegisterTest() {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setUsername("Larry");
        request.setPassword("12345");
        request.setEmail("js6132@columbia.edu");
        BaseResponse response = userService.userRegister(request);
        System.out.println(response);
        Assert.assertEquals(response.getCode(), Status.DUPLICATE_ERROR);
    }

    @Test
    public void userLoginTest() {
        UserLoginRequest request = new UserLoginRequest();
        request.setUsername("Wendy");
        request.setPassword("1234");
        BaseResponse response = userService.userLogin(request);
        System.out.println(response);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
    }
}
