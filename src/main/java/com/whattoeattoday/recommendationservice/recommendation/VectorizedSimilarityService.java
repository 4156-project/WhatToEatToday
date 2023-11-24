package com.whattoeattoday.recommendationservice.recommendation;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.recommendation.request.GetVectorizedSimilarityRankOnMultiFieldRequest;
import com.whattoeattoday.recommendationservice.recommendation.request.GetVectorizedSimilarityRankRequest;
import org.apache.spark.sql.Row;

import java.util.List;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/19/23
 */
public interface VectorizedSimilarityService {
    /**
     * Generate top-k Similar items to the given id, based on the given filed name
     * @param request
     * @return
     */
    BaseResponse<List<Row>> getVectorizedSimilarityRank(GetVectorizedSimilarityRankRequest request);

    /**
     * Generate top-k Similar items to the given id, based on the given filed names
     * @param request
     * @return
     */
    BaseResponse<List<Row>> getVectorizedSimilarityRankOnMultiField(GetVectorizedSimilarityRankOnMultiFieldRequest request);
}
