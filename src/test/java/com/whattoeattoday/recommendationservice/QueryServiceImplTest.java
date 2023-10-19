package com.whattoeattoday.recommendationservice;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.PageInfo;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.query.request.QueryCategoryByNameRequest;
import com.whattoeattoday.recommendationservice.query.request.QueryCategoryInfoRequest;
import com.whattoeattoday.recommendationservice.query.request.QueryContentBySingleConditionRequest;
import com.whattoeattoday.recommendationservice.query.service.api.QueryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
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
public class QueryServiceImplTest {

    @Resource
    private QueryService queryService;

    @Test
    public void testQueryCategoryInfo() {
        QueryCategoryInfoRequest request = new QueryCategoryInfoRequest();
        request.setCategoryName("test1016");
        BaseResponse response = queryService.queryCategoryInfo(request);
        log.info(" TestQueryCategoryInfo RESPONSE: {}", response);
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void testQueryAllCategoryName() {
        BaseResponse response = queryService.queryAllCategoryName();
        log.info("TestQueryAllCategoryName RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.SUCCESS);
    }

    @Test
    public void testQueryCategoryByName() {
        QueryCategoryByNameRequest request = new QueryCategoryByNameRequest();
        request.setCategoryName("test1016");
        request.setContentName("Larry");
        request.setPageNo("1");
        request.setPageSize("2");
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
    public void testQueryContentBySingleCondition() {
        QueryContentBySingleConditionRequest request = new QueryContentBySingleConditionRequest();
        request.setCategoryName("test1016");
        request.setFieldNames(new ArrayList<String>(){{add("*");}});
        request.setConditionField("gender");
        request.setConditionValue("male");
        request.setPageNo("1");
        request.setPageSize("2");
        BaseResponse<PageInfo> response = queryService.queryContentBySingleCondition(request);
        log.info("TestQueryContentBySingleCondition RESPONSE: {}", response);
        Assert.assertNotNull(response.getData().getPageData());
    }

}
