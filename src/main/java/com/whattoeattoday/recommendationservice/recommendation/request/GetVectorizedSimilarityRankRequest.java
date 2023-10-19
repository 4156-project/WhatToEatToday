package com.whattoeattoday.recommendationservice.recommendation.request;

import lombok.Data;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/19/23
 */
@Data
public class GetVectorizedSimilarityRankRequest {
    public String categoryName;
    public String fieldName;
    public String targetId;
    public Integer rankTopSize;
}
