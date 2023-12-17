package com.whattoeattoday.recommendationservice;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.PageInfo;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.database.request.row.DeleteRowRequest;
import com.whattoeattoday.recommendationservice.database.service.impl.TableServiceImpl;
import com.whattoeattoday.recommendationservice.intratable.request.DeleteRequest;
import com.whattoeattoday.recommendationservice.intratable.request.InsertRequest;
import com.whattoeattoday.recommendationservice.intratable.request.UpdateRequest;
import com.whattoeattoday.recommendationservice.intratable.service.api.IntraTableService;
import com.whattoeattoday.recommendationservice.query.request.FuzzySearchContentRequest;
import com.whattoeattoday.recommendationservice.recommendation.request.GetRecommendationOnSimilarUserRequest;
import com.whattoeattoday.recommendationservice.recommendation.service.impl.GetRecommendationServiceImpl;
import com.whattoeattoday.recommendationservice.user.request.UserCollectionRequest;
import com.whattoeattoday.recommendationservice.user.request.UserLoginRequest;
import com.whattoeattoday.recommendationservice.user.request.UserRegisterRequest;
import com.whattoeattoday.recommendationservice.user.service.impl.UserServiceImpl;
import com.whattoeattoday.recommendationservice.query.request.*;
import com.whattoeattoday.recommendationservice.query.service.api.QueryService;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yufeng Wan yw3921@columbia.edu
 * @date 12/10/23
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecommendationServiceApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntegrationTest {
    @Resource
    private UserServiceImpl userService;
    @Resource
    private TableServiceImpl tableService;
    @Resource
    private GetRecommendationServiceImpl getRecommendationService;
    @Resource
    private QueryService queryService;
    @Resource
    private IntraTableService intraTableService;
    @Test
    public void testIntegration() {
        // Register
        UserRegisterRequest request = new UserRegisterRequest();
        request.setUsername("elder4");
        request.setPassword("12345");
        request.setEmail("elder1@163.com");
        request.setCategory("food");
        BaseResponse response = userService.userRegister(request);
        Assert.assertEquals(response.getCode(), Status.SUCCESS);
        // Login
        UserLoginRequest request1 = new UserLoginRequest();
        request1.setUsername("elder4");
        request1.setPassword("12345");
        response = userService.userLogin(request1);
        Assert.assertEquals(response.getCode(), Status.SUCCESS);
        // Add item to collections
        UserCollectionRequest request2 = new UserCollectionRequest();
        request2.setUsername("elder4");
        request2.setPassword("12345");
        request2.setCategory("food");
        request2.setItemId("10");
        response = userService.userAddCollection(request2);
        Assert.assertEquals(response.getCode(), Status.SUCCESS);
        // Delete item from collections
        UserCollectionRequest request3 = new UserCollectionRequest();
        request3.setUsername("elder4");
        request3.setPassword("12345");
        request3.setCategory("food");
        request3.setItemId("10");
        response = userService.userDeleteCollection(request3);
        Assert.assertEquals(response.getCode(), Status.SUCCESS);
        // Group recommendation
        GetRecommendationOnSimilarUserRequest request4 = new GetRecommendationOnSimilarUserRequest();
        request4.setUsername("elder4");
        request4.setPassword("12345");
        request4.setRankTopSize(10);
        BaseResponse<List<String>> results = getRecommendationService.recommendOnSimilarUser(request4);
        Assert.assertEquals(results.getCode(), Status.PARAM_ERROR);
        request4.setCategory("food");
        results = getRecommendationService.recommendOnSimilarUser(request4);
        Assert.assertEquals(results.getCode(), Status.SUCCESS);
        System.out.println(results.getData());
        //Fuzzy search
        FuzzySearchContentRequest request5 = new FuzzySearchContentRequest();
        request5.setCategoryName("food");
        request5.setKeyword("broccoli");
        request5.setPageNo("1");
        request5.setPageSize("10");
        BaseResponse<PageInfo> response2 = queryService.fuzzySearchContent(request5);
        log.info("TestFuzzyQuery RESPONSE: {}", response2);
        Assert.assertTrue(response2.isSuccess());
        //User add an instance to table
        InsertRequest request6 = new InsertRequest();
        request6.setUsername("elder4");
        request6.setPassword("12345");
        request6.setTableName("food");
        Map<String, Object> fieldNameValues = new HashMap<>();
        fieldNameValues.put("title", "testfood");
        fieldNameValues.put("ingredients","Water");
        request6.setFieldNameValues(fieldNameValues);
        BaseResponse response3 = intraTableService.insert(request6);
        log.info("RESPONSE: {}", response3);
        Assert.assertEquals(response3.getCode(), Status.SUCCESS);
        //Delete the instance
        DeleteRequest request7 = new DeleteRequest();
        request7.setUsername("elder4");
        request7.setPassword("12345");
        request7.setTableName("food");
        request7.setConditionField("title");
        request7.setConditionValue("testfood");
        BaseResponse response4 = intraTableService.delete(request7);
        log.info("RESPONSE: {}", response4);
        Assert.assertEquals(response4.getCode(), Status.SUCCESS);
        // Delete the user account
        DeleteRowRequest deleteRowRequest = new DeleteRowRequest();
        deleteRowRequest.setTableName("user");
        deleteRowRequest.setConditionField("username");
        deleteRowRequest.setConditionValue("elder4");
        tableService.delete(deleteRowRequest);
    }

}
