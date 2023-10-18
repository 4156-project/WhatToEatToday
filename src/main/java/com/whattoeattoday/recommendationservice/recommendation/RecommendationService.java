package com.whattoeattoday.recommendationservice.recommendation;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.recommendation.request.GetRecommendationBySingleContentRequest;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/18/23
 */
public interface RecommendationService {
    public BaseResponse getRecommendationBySingleContent(GetRecommendationBySingleContentRequest request);
}
