package com.whattoeattoday.recommendationservice;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.PageInfo;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.query.request.QueryCategoryByNameRequest;
import com.whattoeattoday.recommendationservice.query.service.api.QueryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

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
    public void TestQueryAllCategory() {
        BaseResponse response = queryService.queryAllCategory();
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.SUCCESS);
    }

    @Test
    public void TestQueryCategoryByName() {
        QueryCategoryByNameRequest request = new QueryCategoryByNameRequest();
        request.setCategoryName("test1016");
        request.setContentName("Larry");
        request.setPageNo("1");
        request.setPageSize("2");
        BaseResponse<PageInfo> response = queryService.queryCategoryByName(request);
        log.info("RESPONSE: {}", response);
        Assert.assertTrue(response.isSuccess());
        Assert.assertFalse(response.getData().getPageData().isEmpty());

        request.setPageNo("2");
        request.setPageSize("1");
        response = queryService.queryCategoryByName(request);
        log.info("RESPONSE: {}", response);
        Assert.assertTrue(response.isSuccess());
        Assert.assertTrue(response.getData().getPageData().isEmpty());
    }

}
