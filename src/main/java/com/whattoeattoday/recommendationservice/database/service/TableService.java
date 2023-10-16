package com.whattoeattoday.recommendationservice.database.service;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.database.request.row.DeleteRowRequest;
import com.whattoeattoday.recommendationservice.database.request.row.InsertRowRequest;
import com.whattoeattoday.recommendationservice.database.request.row.QueryRowRequest;
import com.whattoeattoday.recommendationservice.database.request.row.UpdateRowRequest;
import com.whattoeattoday.recommendationservice.database.response.QueryRowResponse;

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
    BaseResponse insert(InsertRowRequest request);

    /**
     * delete one row by condition
     * @param request
     * @return
     */
    BaseResponse delete(DeleteRowRequest request);

    /**
     * update one row by condition
     * @param request
     * @return
     */
    BaseResponse update(UpdateRowRequest request);

    /**
     * query rows with or without condition
     * @param request if condition is null, return all rows
     * @return
     */
    QueryRowResponse query(QueryRowRequest request);
}
