package com.whattoeattoday.recommendationservice.query.service.api;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.query.request.*;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/13/23
 */
public interface QueryService {

    BaseResponse queryByName(QueryByNameRequest request);

    /**
     * Query Info about the given category name
     * @param request
     * @return
     */
    BaseResponse queryCategoryByName(QueryCategoryByNameRequest request);

    /**
     * List info about all categories
     * @return
     */
    BaseResponse queryAllCategory();

    /**
     * Query contents by one content name
     * @param request
     * @return
     */
    BaseResponse queryContentByName(QueryContentByNameRequest request);

    /**
     * Query Contents in a given table with multiple search conditions
     * @param request
     * @return
     */
    BaseResponse queryContentByMultiCondition(QueryContentByMultiConditionRequest request);

    /**
     * Fuzzy search contents by keyword
     * @param request
     * @return
     */
    BaseResponse fuzzySearchContent(FuzzySearchContentRequest request);
}
