package com.whattoeattoday.recommendationservice.recommendation.controller;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.recommendation.VectorizedSimilarityService;
import com.whattoeattoday.recommendationservice.recommendation.request.GetVectorizedSimilarityRankOnMultiFieldRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 11/24/23
 */
@RestController
public class RecommendationController {
    @Resource
    public VectorizedSimilarityService vectorizedSimilarityService;

    /**
     * Generate top-k Similar items to the given id, based on the given filed names
     * @param request
     */
    @PostMapping("/recommend/item")
    public BaseResponse<List<String>> recommendOnItem(@RequestBody GetVectorizedSimilarityRankOnMultiFieldRequest request) throws IOException, ExecutionException, InterruptedException {
        return vectorizedSimilarityService.getVectorizedSimilarityRankOnMultiField(request);
    }
}
