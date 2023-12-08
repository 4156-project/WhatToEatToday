package com.whattoeattoday.recommendationservice;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.database.request.row.DeleteRowRequest;
import com.whattoeattoday.recommendationservice.database.service.impl.TableServiceImpl;
import com.whattoeattoday.recommendationservice.recommendation.request.GetRecommendationOnSimilarUserRequest;
import com.whattoeattoday.recommendationservice.recommendation.service.impl.GetRecommendationServiceImpl;
import com.whattoeattoday.recommendationservice.user.request.UserCollectionRequest;
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
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecommendationServiceApplication.class)
public class UserServiceImplTest {

    @Resource
    private UserServiceImpl userService;
    @Resource
    private TableServiceImpl tableService;
    @Resource
    private GetRecommendationServiceImpl getRecommendationService;

    @Test
    public void userRegisterTest() {
        UserRegisterRequest request = new UserRegisterRequest();
        // test username not found
        BaseResponse response = userService.userRegister(request);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        request.setUsername("");
        response = userService.userRegister(request);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        // test password not found
        request.setUsername("Larry");
        response = userService.userRegister(request);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        request.setPassword("");
        response = userService.userRegister(request);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        // test email not found
        request.setPassword("12345");
        response = userService.userRegister(request);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        request.setEmail("");
        response = userService.userRegister(request);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        // test email not valid
        request.setEmail("12345");
        response = userService.userRegister(request);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
        // test category not found
        request.setEmail("js6132@columbia.edu");
        response = userService.userRegister(request);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        request.setCategory("");
        response = userService.userRegister(request);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        request.setCategory("aaaa");
        response = userService.userRegister(request);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        // test duplicate
        request.setCategory("food");
        response = userService.userRegister(request);
        Assert.assertEquals(response.getCode(), Status.DUPLICATE_ERROR);
        // test register successfully
        request.setUsername("elder4");
        request.setPassword("12345");
        request.setEmail("elder1@163.com");
        request.setCategory("food");
        response = userService.userRegister(request);
        Assert.assertEquals(response.getCode(), Status.SUCCESS);
        // delete user
        DeleteRowRequest deleteRowRequest = new DeleteRowRequest();
        deleteRowRequest.setTableName("user");
        deleteRowRequest.setConditionField("username");
        deleteRowRequest.setConditionValue("elder4");
        tableService.delete(deleteRowRequest);
    }

    @Test
    public void userLoginTest() {
        UserLoginRequest request = new UserLoginRequest();
        // test username not found
        BaseResponse response = userService.userLogin(request);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        request.setUsername("");
        response = userService.userLogin(request);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        // test password not found
        request.setUsername("Larry");
        response = userService.userLogin(request);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        request.setPassword("");
        response = userService.userLogin(request);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        // test password not valid
        request.setPassword("22222");
        response = userService.userLogin(request);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
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
        request.setUsername("");
        response = userService.userVerify(request);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        // check password not found
        request.setUsername("Larry");
        response = userService.userVerify(request);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        request.setPassword("");
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

    @Test
    public void userAddCollectionTest() {
        UserCollectionRequest request = new UserCollectionRequest();
        // check user not valid
        BaseResponse response = userService.userAddCollection(request);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
        // check Category Not Valid
        request.setUsername("Nick");
        request.setPassword("54321");
        response = userService.userAddCollection(request);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
        // check Item Not Valid
        request.setCategory("food");
        request.setItemId("-1");
        response = userService.userAddCollection(request);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
        // check Item has Existed
        request.setItemId("1");
        response = userService.userAddCollection(request);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
        // check Add Collection Success
        request.setItemId("10");
        response = userService.userAddCollection(request);
        Assert.assertEquals(response.getCode(), Status.SUCCESS);
    }

//    @Test
//    public void userAddCollectionTest2() {
//        UserCollectionRequest request = new UserCollectionRequest();
//        // check Category Not Valid
//        request.setUsername("elder3");
//        request.setPassword("12345");
//        request.setCategory("food");
//        request.setItemId("1005");
//        BaseResponse response = userService.userAddCollection(request);
//        request.setItemId("337");
//        response = userService.userAddCollection(request);
//        request.setItemId("10658");
//        response = userService.userAddCollection(request);
//        request.setItemId("992");
//        response = userService.userAddCollection(request);
//        Assert.assertEquals(response.getCode(), Status.SUCCESS);
//    }

    @Test
    public void userDeleteCollectionTest() {
        UserCollectionRequest request = new UserCollectionRequest();
        // check user not valid
        BaseResponse response = userService.userDeleteCollection(request);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
        // check Category or Item Not Valid
        request.setUsername("Nick");
        request.setPassword("54321");
        request.setItemId("100");
        response = userService.userDeleteCollection(request);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
        // check Item not has Existed
        request.setCategory("food");
        response = userService.userDeleteCollection(request);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
        // check item not valid
        request.setItemId("-1");
        response = userService.userDeleteCollection(request);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
        // check delete Collection Success
        request.setItemId("10");
        response = userService.userDeleteCollection(request);
        Assert.assertEquals(response.getCode(), Status.SUCCESS);
    }

    @Test
    public void test03RecommendOnSimilarUsers() {
        GetRecommendationOnSimilarUserRequest request = new GetRecommendationOnSimilarUserRequest();
        request.setUsername("elder1");
        request.setPassword("12345");
        request.setRankTopSize(10);
        BaseResponse<List<String>> results = getRecommendationService.recommendOnSimilarUser(request);
        Assert.assertEquals(results.getCode(), Status.PARAM_ERROR);
        request.setCategory("food");
        results = getRecommendationService.recommendOnSimilarUser(request);
        Assert.assertEquals(results.getCode(), Status.SUCCESS);
        System.out.println(results.getData());
    }
}
