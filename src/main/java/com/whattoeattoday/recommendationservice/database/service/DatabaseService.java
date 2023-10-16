package com.whattoeattoday.recommendationservice.database.service;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.database.request.BuildTableRequest;
import com.whattoeattoday.recommendationservice.database.request.QueryTableRequest;

public interface DatabaseService {
    BaseResponse buildTable(BuildTableRequest request);
    BaseResponse deleteTable(QueryTableRequest request);
    BaseResponse queryTable(QueryTableRequest request);
}
