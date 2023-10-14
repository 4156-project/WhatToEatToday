package com.whattoeattoday.recommendationservice.query.service.impl;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.query.request.QueryByNameRequest;
import com.whattoeattoday.recommendationservice.query.service.api.QueryService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/13/23
 */
@Service
public class QueryServiceImpl implements QueryService {

    @Override
    public BaseResponse queryByName(QueryByNameRequest request) {
        String categoryName = request.getCategoryName();
        // TODO: use other service to query databse and get query result;
        Map<String, String> result = new HashMap<>();
        return BaseResponse.withSuccess(result);
    }
}
