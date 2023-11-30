package com.whattoeattoday.recommendationservice.query.service.api;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.PageInfo;
import com.whattoeattoday.recommendationservice.database.response.QueryTableResponse;
import com.whattoeattoday.recommendationservice.query.request.*;

import java.util.Set;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/13/23
 */
public interface QueryService {

    /**
     * List info about one category, including category name, field name, field type, row nums etc.
     * @param request
     * @return
     */
    BaseResponse<QueryTableResponse> queryCategoryInfo(QueryCategoryInfoRequest request);

    /**
     * Query Info about the given category name
     * @param request
     * @return
     */
    BaseResponse<PageInfo> queryCategoryByName(QueryCategoryByNameRequest request);

    /**
     * List names of all categories
     * @return
     */
    BaseResponse<Set<String>> queryAllCategoryName();

    /**
     * Query contents by one content name
     * @param request
     * @return
     */
    BaseResponse<PageInfo> queryContentBySingleCondition(QueryContentBySingleConditionRequest request);

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
    BaseResponse<PageInfo> fuzzySearchContent(FuzzySearchContentRequest request);

    /**
     * Query contents in a given category
     * @param request
     * @return
     */
    BaseResponse<PageInfo> queryContent(QueryContentRequest request);
}
