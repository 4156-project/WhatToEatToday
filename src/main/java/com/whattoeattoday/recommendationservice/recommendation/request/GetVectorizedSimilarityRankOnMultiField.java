package com.whattoeattoday.recommendationservice.recommendation.request;

import lombok.Data;

import java.util.List;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/19/23
 */
@Data
public class GetVectorizedSimilarityRankOnMultiField {
    public String categoryName;
    public List<String> fieldNameList;
    public String targetId;
    public Integer rankTopSize;
}
