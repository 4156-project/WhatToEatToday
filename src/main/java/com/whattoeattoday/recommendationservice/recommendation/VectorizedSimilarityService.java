package com.whattoeattoday.recommendationservice.recommendation;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.recommendation.request.GetRecommendationOnUserRequest;
import com.whattoeattoday.recommendationservice.recommendation.request.GetVectorizedSimilarityRankOnMultiFieldRequest;
import org.apache.spark.sql.Row;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/19/23
 */
public interface VectorizedSimilarityService {
    /**
     * Generate top-k Similar items to the given id, based on the given user
     * @param request
     * @return
     */
    BaseResponse<List<String>> getRecommendationOnUser(GetRecommendationOnUserRequest request) throws IOException;

    /**
     * Generate top-k Similar items to the given id, based on the given filed names
     * @param request
     * @return
     */
    BaseResponse<List<String>> getVectorizedSimilarityRankOnMultiField(GetVectorizedSimilarityRankOnMultiFieldRequest request) throws IOException, ExecutionException, InterruptedException;
}
