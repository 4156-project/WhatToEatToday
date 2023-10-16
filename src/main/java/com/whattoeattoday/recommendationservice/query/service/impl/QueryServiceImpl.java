package com.whattoeattoday.recommendationservice.query.service.impl;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.PageInfo;
import com.whattoeattoday.recommendationservice.common.ParamUtil;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.database.response.QueryTableNamesResponse;
import com.whattoeattoday.recommendationservice.database.service.DatabaseService;
import com.whattoeattoday.recommendationservice.query.request.*;
import com.whattoeattoday.recommendationservice.query.service.api.QueryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/13/23
 */
@Service
public class QueryServiceImpl implements QueryService {

    @Resource
    private DatabaseService databaseService;

    @Override
    public BaseResponse queryCategoryByName(QueryCategoryByNameRequest request) {
        if (ParamUtil.isBlank(request.getCategoryName()) || ParamUtil.isBlank(request.getPageNo()) || ParamUtil.isBlank(request.getPageSize())) {
            return BaseResponse.with(Status.PARAM_ERROR, "Param is Null");
        }
        if (!ParamUtil.isNumeric(request.getPageNo()) || !ParamUtil.isNumeric(request.getPageSize())) {
            return BaseResponse.with(Status.PARAM_ERROR, "Page Number or Page Size is not Numeric");
        }
        String categoryName = request.getCategoryName();
        if (!databaseService.queryTableNames().tableNames.contains(categoryName)) {
            return BaseResponse.with(Status.NOT_FOUND, "Category Not Found");
        }

        PageInfo pageInfo = PageInfo.builder()
                .pageNo(Integer.valueOf(request.getPageNo()))
                .pageSize(Integer.valueOf(request.getPageSize()))
                .build();
        // TODO
        return null;
    }

    @Override
    public BaseResponse queryAllCategory() {
        QueryTableNamesResponse response = databaseService.queryTableNames();
        if (response.tableNames == null || response.tableNames.isEmpty()) {
            return BaseResponse.with(Status.SUCCESS, "Database is Empty");
        }
        return BaseResponse.with(Status.SUCCESS, response.tableNames);
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
