package com.whattoeattoday.recommendationservice.database.service;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.database.request.BuildTableRequest;
import com.whattoeattoday.recommendationservice.database.request.DeleteTableRequest;
import com.whattoeattoday.recommendationservice.database.request.QueryTableRequest;
import com.whattoeattoday.recommendationservice.database.response.QueryTableNamesResponse;

public interface DatabaseService {
    BaseResponse buildTable(BuildTableRequest request);
    BaseResponse deleteTable(DeleteTableRequest request);
    BaseResponse queryTable(QueryTableRequest request);

    /**
     * Query names of all tables
     *
     * @return
     */
    QueryTableNamesResponse queryTableNames();
}
