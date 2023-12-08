package com.whattoeattoday.recommendationservice.recommendation.request;

import lombok.Data;

import java.util.List;

/**
 * @author Jiarong Shi js6132@columbia.edu
 * @date 12/08/23
 */

@Data
public class GetRecommendationOnSimilarUserRequest {
    public String username;
    public String password;
    public String category;
    public Integer rankTopSize;
}
