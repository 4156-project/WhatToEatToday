package com.whattoeattoday.recommendationservice;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.PageInfo;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.database.service.DatabaseService;
import com.whattoeattoday.recommendationservice.query.request.*;
import com.whattoeattoday.recommendationservice.query.service.api.QueryService;
import com.whattoeattoday.recommendationservice.query.service.impl.QueryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/16/23
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecommendationServiceApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QueryServiceImplTest {

    @Resource
    private QueryService queryService;

    @Test
    public void test0QueryCategoryInfo() {
        QueryCategoryInfoRequest request = new QueryCategoryInfoRequest();
        Assert.assertFalse(queryService.queryCategoryInfo(request).isSuccess());
        request.setCategoryName("testNotExists");
        Assert.assertFalse(queryService.queryCategoryInfo(request).isSuccess());
        request.setCategoryName("test1016");
        BaseResponse response = queryService.queryCategoryInfo(request);
        log.info(" TestQueryCategoryInfo RESPONSE: {}", response);
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void test1QueryAllCategoryName() {
        BaseResponse response = queryService.queryAllCategoryName();
        log.info("TestQueryAllCategoryName RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.SUCCESS);
    }

    @Test
    public void test2QueryCategoryByName() {
        QueryCategoryByNameRequest request = new QueryCategoryByNameRequest();
        Assert.assertFalse(queryService.queryCategoryByName(request).isSuccess());
        request.setCategoryName("fakeName");
        Assert.assertFalse(queryService.queryCategoryByName(request).isSuccess());
        request.setContentName("Larry");
        Assert.assertFalse(queryService.queryCategoryByName(request).isSuccess());
        request.setPageNo("1");
        Assert.assertFalse(queryService.queryCategoryByName(request).isSuccess());
        request.setPageSize("2");
        Assert.assertFalse(queryService.queryCategoryByName(request).isSuccess());
        request.setCategoryName("test1016");
        BaseResponse<PageInfo> response = queryService.queryCategoryByName(request);
        log.info(" TestQueryCategoryByName RESPONSE1: {}", response);
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getData().getPageData());

        request.setPageNo("2");
        request.setPageSize("1");
        response = queryService.queryCategoryByName(request);
        log.info("TestQueryCategoryByName RESPONSE2: {}", response);
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getData().getPageData());
    }

    @Test
    public void test3QueryContentBySingleCondition() {
        QueryContentBySingleConditionRequest request = new QueryContentBySingleConditionRequest();
        // param incomplete
        Assert.assertFalse(queryService.queryContentBySingleCondition(request).isSuccess());
        request.setCategoryName("fakeName");
        request.setFieldNames(new ArrayList<String>(){{add("fieldFakeName");}});
        request.setConditionField("gender");
        request.setConditionValue("male");
        request.setPageNo("1");
        request.setPageSize("2");
        //category not found
        Assert.assertFalse(queryService.queryContentBySingleCondition(request).isSuccess());
        request.setCategoryName("test1016");
        // field name error
        Assert.assertFalse(queryService.queryContentBySingleCondition(request).isSuccess());
        request.setFieldNames(new ArrayList<String>(){{add("*");}});
        BaseResponse<PageInfo> response = queryService.queryContentBySingleCondition(request);
        log.info("TestQueryContentBySingleCondition RESPONSE: {}", response);
        Assert.assertNotNull(response.getData().getPageData());
    }

    @Test
    public void test4QueryContentInCategory() {
        QueryContentRequest request = new QueryContentRequest();
        request.setCategoryName("test1016");
        request.setPageNo("1");
        request.setPageSize("3");
        BaseResponse<PageInfo> pageInfoBaseResponse = queryService.queryContent(request);
        log.info("TestQueryContentInCategory RESPONSE: {}", pageInfoBaseResponse);
    }

    @Test
    public void test05FuzzySearch() {
        FuzzySearchContentRequest request = new FuzzySearchContentRequest();
        request.setCategoryName("food");
        request.setKeyword("broccoli");
        request.setPageSize("10");
        request.setPageNo("1");
        BaseResponse<PageInfo> response = queryService.fuzzySearchContent(request);
        log.info("TestFuzzyQuery RESPONSE: {}", response);
        Assert.assertTrue(response.isSuccess());
    }

}
