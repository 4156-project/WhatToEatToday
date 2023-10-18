package com.whattoeattoday.recommendationservice.intertable.service.api;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.database.request.table.BuildTableRequest;
import com.whattoeattoday.recommendationservice.database.request.table.DeleteTableRequest;
import com.whattoeattoday.recommendationservice.database.request.table.UpdateTableRequest;

/**
 * @author Ruoxuan wANG rw2961@columbia.edu
 * @date 10/17/2023
 */
public interface InterTableService {

    /**
     * Create a new table
     * @param request
     * @return
     */
    BaseResponse<Object> createTable(BuildTableRequest request);

    /**
     * Delete a table
     * @param request
     * @return
     */
    BaseResponse<Object> deleteTable(DeleteTableRequest request);

    /**
     * Set a column to be auto incremented
     * @param request
     * @return
     */
    BaseResponse setAutoIncrement(UpdateTableRequest request);
}
