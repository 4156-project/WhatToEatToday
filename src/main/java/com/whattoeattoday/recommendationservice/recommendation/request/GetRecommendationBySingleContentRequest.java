package com.whattoeattoday.recommendationservice.recommendation.request;

import lombok.Data;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/18/23
 */
@Data
public class GetRecommendationBySingleContentRequest {
    String contentId;
    String categoryName;
}
