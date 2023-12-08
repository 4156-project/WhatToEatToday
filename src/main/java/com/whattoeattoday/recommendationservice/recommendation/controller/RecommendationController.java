package com.whattoeattoday.recommendationservice.recommendation.controller;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.recommendation.request.GetRecommendationOnSimilarUserRequest;
import com.whattoeattoday.recommendationservice.recommendation.service.GetRecommendationService;
import com.whattoeattoday.recommendationservice.recommendation.request.GetRecommendationOnUserRequest;
import com.whattoeattoday.recommendationservice.recommendation.request.GetRecommendationOnItemRequest;
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
    public GetRecommendationService getRecommendationService;

    /**
     * Generate top-k Similar items to the given item id, based on the given field names
     * @param request
     */
    @PostMapping("/recommend/item")
    public BaseResponse<List<String>> recommendOnItem(@RequestBody GetRecommendationOnItemRequest request) throws IOException, ExecutionException, InterruptedException {
        return getRecommendationService.getRecommendationOnItem(request);
    }

    /**
     * Generate top-k similar items to the given user, based on whose collection
     * @param request
     * @return
     */
    @PostMapping("/recommend/user")
    public BaseResponse<List<String>> recommendOnUser(@RequestBody GetRecommendationOnUserRequest request){
        return getRecommendationService.getRecommendationOnUser(request);
    }

    /**
     * Generate top-k similar items to the given user, based on whose collection
     * @param request
     * @return
     */
    @PostMapping("/recommend/similar-user")
    public BaseResponse<List<String>> recommendOnSimilarUser(@RequestBody GetRecommendationOnSimilarUserRequest request){
        return getRecommendationService.recommendOnSimilarUser(request);
    }
}
