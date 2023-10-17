package com.whattoeattoday.recommendationservice.database.service;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.database.request.table.BuildTableRequest;
import com.whattoeattoday.recommendationservice.database.request.table.DeleteTableRequest;
import com.whattoeattoday.recommendationservice.database.request.table.QueryTableRequest;
import com.whattoeattoday.recommendationservice.database.request.table.UpdateTableRequest;
import com.whattoeattoday.recommendationservice.database.response.QueryTableNamesResponse;
import com.whattoeattoday.recommendationservice.database.response.QueryTableResponse;

/**
 * @author Jiarong Shi js6132@columbia.edu
 * @date 10/15/23
 */
public interface DatabaseService {
    /**
     * build one table from DB
     * @param request
     * @return
     */
    BaseResponse buildTable(BuildTableRequest request);

    /**
     * delete one table from DB
     * @param request
     * @return
     */
    BaseResponse deleteTable(DeleteTableRequest request);

    /**
     * Query table from DB by table name
     * @param request
     * @return
     */
    QueryTableResponse queryTable(QueryTableRequest request);

    /**
     * Query names of all table
     *
     * @return
     */
    QueryTableNamesResponse queryTableNames();

    /**
     * set specified column to auto increment
     *
     * @return
     */
    BaseResponse setAutoIncrement(UpdateTableRequest request);

    /**
     * set specified column to be unique
     *
     * @return
     */
    BaseResponse setUniqueKey(UpdateTableRequest request);
}
