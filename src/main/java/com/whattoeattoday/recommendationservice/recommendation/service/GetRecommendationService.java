package com.whattoeattoday.recommendationservice.recommendation.service;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.recommendation.request.GetRecommendationOnUserRequest;
import com.whattoeattoday.recommendationservice.recommendation.request.GetRecommendationOnItemRequest;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/19/23
 */
public interface GetRecommendationService {
    /**
     * Generate top-k Similar items to the given id, based on the given user
     * @param request
     * @return
     */
    BaseResponse<List<String>> getRecommendationOnUser(GetRecommendationOnUserRequest request);

    /**
     * Generate top-k Similar items to the given id, based on the given filed names
     * @param request
     * @return
     */
    BaseResponse<List<String>> getRecommendationOnItem(GetRecommendationOnItemRequest request) throws IOException, ExecutionException, InterruptedException;
}
