package com.whattoeattoday.recommendationservice.query.service.impl;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.query.request.*;
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

    @Override
    public BaseResponse queryCategoryByName(QueryCategoryByNameRequest request) {
        String categoryName = request.getCategoryName();
        // TODO
        return null;
    }

    @Override
    public BaseResponse queryAllCategory() {
        // TODO
        return null;
    }

    @Override
    public BaseResponse queryContentByName(QueryContentByNameRequest request) {
        String tableName = request.getCategoryName();
        String fileName = request.getContentName();
        return null;
    }

    @Override
    public BaseResponse queryContentByMultiCondition(QueryContentByMultiConditionRequest request) {
        String tableName = request.getCategoryName();
        List<String> filedNameList = request.getFiledNameList();
        List<String> filedValueList = request.getFiledValueList();
        return null;
    }

    @Override
    public BaseResponse fuzzySearchContent(FuzzySearchContentRequest request) {
        String tableName = request.getCategoryName();
        String keyword = request.getKeyword();
        return null;
    }
}
