package com.whattoeattoday.recommendationservice;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.recommendation.VectorizedSimilarityService;
import com.whattoeattoday.recommendationservice.recommendation.request.GetRecommendationOnUserRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 11/28/23
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecommendationServiceApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RecommendTest {

    @Autowired
    public VectorizedSimilarityService vectorizedSimilarityService;

    @Test
    public void test01RecommendOnUser() {
        GetRecommendationOnUserRequest request = new GetRecommendationOnUserRequest();
        request.setUserId("1");
        request.setCategoryName("movies");
        request.setFieldNameList(new ArrayList<String>(){{add("genre"); add("rating");}});
        request.setRankTopSize(10);
        BaseResponse<List<String>> recommendationOnUser = vectorizedSimilarityService.getRecommendationOnUser(request);
        System.out.println(recommendationOnUser);
    }
}
