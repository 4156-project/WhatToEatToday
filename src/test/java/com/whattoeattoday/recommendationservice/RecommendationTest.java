package com.whattoeattoday.recommendationservice;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.PageInfo;
import com.whattoeattoday.recommendationservice.query.request.QueryContentBySingleConditionRequest;
import com.whattoeattoday.recommendationservice.query.service.api.QueryService;
import com.whattoeattoday.recommendationservice.recommendation.VectorizedSimilarityService;
import com.whattoeattoday.recommendationservice.recommendation.request.GetVectorizedSimilarityRankRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.Row;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    public void testGetVectorizedSimilarityRank() {
        GetVectorizedSimilarityRankRequest request = new GetVectorizedSimilarityRankRequest();
        request.setCategoryName("food");
        request.setFieldName("ingredients");
        request.setTargetId("6");
        request.setRankTopSize(15);
        BaseResponse<List<Row>> vectorizedSimilarityRank = vectorizedSimilarityService.getVectorizedSimilarityRank(request);
        log.info("testGetVectorizedSimilarityRank response: {}", vectorizedSimilarityRank.getData());

        List<Row> resultRows = vectorizedSimilarityRank.getData();
        log.info("Target Content id: {}", resultRows.get(0).get(0));
        for (Row resultRow : resultRows) {
            String id = String.valueOf(resultRow.getInt(0));
            QueryContentBySingleConditionRequest request1 = new QueryContentBySingleConditionRequest();
            request1.setCategoryName("food");
            request1.setConditionField("id");
            request1.setConditionValue(id);
            request1.setFieldNames(new ArrayList<String>(){{add("*");}});
            request1.setPageNo("1");
            request1.setPageSize("1");
            BaseResponse<PageInfo> pageInfoBaseResponse = queryService.queryContentBySingleCondition(request1);
            log.info("{}, {}", id, pageInfoBaseResponse.getData().getPageData().get(0).get("title"));
        }
    }
}
