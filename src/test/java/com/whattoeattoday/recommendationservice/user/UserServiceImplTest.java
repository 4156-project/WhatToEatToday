package com.whattoeattoday.recommendationservice.user;

import com.whattoeattoday.recommendationservice.RecommendationServiceApplication;
import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.database.request.row.DeleteRowRequest;
import com.whattoeattoday.recommendationservice.database.service.impl.TableServiceImpl;
import com.whattoeattoday.recommendationservice.user.request.UserLoginRequest;
import com.whattoeattoday.recommendationservice.user.request.UserRegisterRequest;
import com.whattoeattoday.recommendationservice.user.request.UserVerifyRequest;
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

    @Resource
    private TableServiceImpl tableService;

    @Test
    public void userRegisterTest() {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setUsername("Larry");
        request.setPassword("12345");
        request.setEmail("js6132@columbia.edu");
        request.setCategory("food");
        // test duplicate
        BaseResponse response = userService.userRegister(request);
        System.out.println(response);
        Assert.assertEquals(response.getCode(), Status.DUPLICATE_ERROR);
        // test username not found
        request.setUsername("");
        response = userService.userRegister(request);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        // test password not found
        request.setPassword("");
        response = userService.userRegister(request);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        // test email not found
        request.setEmail("");
        response = userService.userRegister(request);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        // test category not found
        request.setCategory("books");
        response = userService.userRegister(request);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        // test register successfully
        request.setUsername("Wendy");
        request.setPassword("54321");
        request.setEmail("wd4672@columbia.edu");
        request.setCategory("food");
        response = userService.userRegister(request);
        Assert.assertEquals(response.getCode(), Status.SUCCESS);
        // delete user
        DeleteRowRequest deleteRowRequest = new DeleteRowRequest();
        deleteRowRequest.setTableName("user");
        deleteRowRequest.setConditionField("username");
        deleteRowRequest.setConditionValue("Wendy");
        tableService.delete(deleteRowRequest);
    }

    @Test
    public void userLoginTest() {
        UserLoginRequest request = new UserLoginRequest();
        request.setUsername("Larry");
        request.setPassword("1234");
        // test password not valid
        BaseResponse response = userService.userLogin(request);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
        // test username not found
        request.setUsername("");
        response = userService.userLogin(request);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        // test password not found
        request.setPassword("");
        response = userService.userLogin(request);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        // test user not found
        request.setUsername("Wendy");
        request.setPassword("12345");
        response = userService.userLogin(request);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        // test login success
        request.setUsername("Larry");
        response = userService.userLogin(request);
        Assert.assertEquals(response.getCode(), Status.SUCCESS);
    }

    @Test
    public void userVerifyTest() {
        UserVerifyRequest request = new UserVerifyRequest();
        // check username not found
        BaseResponse response = userService.userVerify(request);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        // check password not found
        request.setUsername("Larry");
        response = userService.userVerify(request);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        // check category not found
        request.setPassword("12345");
        response = userService.userVerify(request);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        // check user not valid
        request.setCategory("movies");
        response = userService.userVerify(request);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
        // check verify success
        request.setCategory("food");
        response = userService.userVerify(request);
        Assert.assertEquals(response.getCode(), Status.SUCCESS);
    }
}
