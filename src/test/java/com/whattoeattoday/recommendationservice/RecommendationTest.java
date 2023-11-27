package com.whattoeattoday.recommendationservice;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.PageInfo;
import com.whattoeattoday.recommendationservice.query.request.QueryContentBySingleConditionRequest;
import com.whattoeattoday.recommendationservice.query.service.api.QueryService;
import com.whattoeattoday.recommendationservice.recommendation.VectorizedSimilarityService;
import com.whattoeattoday.recommendationservice.recommendation.request.GetVectorizedSimilarityRankOnMultiFieldRequest;
import com.whattoeattoday.recommendationservice.recommendation.request.GetVectorizedSimilarityRankRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.Row;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/19/23
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecommendationServiceApplication.class)
public class RecommendationTest {
    @Resource
    private VectorizedSimilarityService vectorizedSimilarityService;

    @Resource
    private QueryService queryService;

    @Test
    public void test() throws IOException, ExecutionException, InterruptedException {
        GetVectorizedSimilarityRankOnMultiFieldRequest request = new GetVectorizedSimilarityRankOnMultiFieldRequest();
        request.setCategoryName("movies");
        request.setFieldNameList(new ArrayList<String>(){{add("genre");add("rating");add("star");}});
        request.setTargetId("1");
        request.setRankTopSize(15);
        BaseResponse<List<String>> vectorizedSimilarityRankOnMultiFieldResponse = vectorizedSimilarityService.getVectorizedSimilarityRankOnMultiField(request);
        List<String> resultRows = vectorizedSimilarityRankOnMultiFieldResponse.getData();
        log.info("Target Content id: {}", resultRows.get(0));
        for (String resultRow : resultRows) {
            String id = String.valueOf(resultRow);
            QueryContentBySingleConditionRequest request1 = new QueryContentBySingleConditionRequest();
            request1.setCategoryName("movies");
            request1.setConditionField("id");
            request1.setConditionValue(id);
            request1.setFieldNames(new ArrayList<String>(){{add("*");}});
            request1.setPageNo("1");
            request1.setPageSize("1");
            BaseResponse<PageInfo> pageInfoBaseResponse = queryService.queryContentBySingleCondition(request1);
            log.info("name: {}, genre: {}, rating: {}, start: {}",
                    pageInfoBaseResponse.getData().getPageData().get(0).get("name"),
                    pageInfoBaseResponse.getData().getPageData().get(0).get("genre"),
                    pageInfoBaseResponse.getData().getPageData().get(0).get("rating"),
                    pageInfoBaseResponse.getData().getPageData().get(0).get("star")
                    );
        }
    }
}
