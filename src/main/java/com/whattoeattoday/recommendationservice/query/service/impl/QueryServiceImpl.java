package com.whattoeattoday.recommendationservice.query.service.impl;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.query.request.QueryByNameRequest;
import com.whattoeattoday.recommendationservice.query.service.api.QueryService;
import org.springframework.stereotype.Service;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/13/23
 */
@Service
public class QueryServiceImpl implements QueryService {
    @Override
    public BaseResponse queryByName(QueryByNameRequest request) {
        String categoryName = request.getCategoryName();
        return null;
    }
}
