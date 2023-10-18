package com.whattoeattoday.recommendationservice.intertable.service.api;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.database.request.table.BuildTableRequest;

/**
 * @author Ruoxuan wANG rw2961@columbia.edu
 * @Date 10/17/2023
 */
public interface InterTableService {

    /**
     * Create a new table
     * @param request
     * @return
     */
    BaseResponse createTable(BuildTableRequest request);
}
