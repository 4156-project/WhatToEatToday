package com.whattoeattoday.recommendationservice.query.service.api;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.query.request.QueryByNameRequest;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/13/23
 */
public interface QueryService {

    BaseResponse queryByName(QueryByNameRequest request);
}
