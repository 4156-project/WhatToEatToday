package com.whattoeattoday.recommendationservice.recommendation.request;

import lombok.Data;

import java.util.List;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 11/28/23
 */
@Data
public class GetRecommendationOnUserRequest {
    public String categoryName;
    public List<String> fieldNameList;
    public String userId;
    public Integer rankTopSize;
}
