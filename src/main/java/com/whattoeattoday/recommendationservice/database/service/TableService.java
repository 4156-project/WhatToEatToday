package com.whattoeattoday.recommendationservice.database.service;

import com.google.api.Page;
import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.PageInfo;
import com.whattoeattoday.recommendationservice.database.request.row.*;
import com.whattoeattoday.recommendationservice.query.request.FuzzySearchContentRequest;

/**
 * @author Jiarong Shi js6132@columbia.edu
 * @date 10/16/23
 */
public interface TableService {
    /**
     * insert one row into specified table
     * @param request
     * @return
     */
    BaseResponse<Object> insert(InsertRowRequest request);

    /**
     * delete one row by condition
     * @param request
     * @return
     */
    BaseResponse<Object> delete(DeleteRowRequest request);

    /**
     * delete one row by multiple condition
     * @param request
     * @return
     */
    BaseResponse<Object> delete(DeleteRowPlusRequest request);

    /**
     * update one row by condition
     * @param request
     * @return
     */
    BaseResponse<Object> update(UpdateRowRequest request);

    /**
     * query rows with or without condition
     * @param request if condition is null, return all rows
     * @return
     */
    PageInfo query(QueryRowRequest request);

    /**
     * fuzzy search with key word
     * @param request
     * @return
     */
    PageInfo fuzzySearch(FuzzySearchContentRequest request);
}
