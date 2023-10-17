package com.whattoeattoday.recommendationservice.query.service.impl;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.PageInfo;
import com.whattoeattoday.recommendationservice.common.ParamUtil;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.database.request.row.QueryRowRequest;
import com.whattoeattoday.recommendationservice.database.request.table.QueryTableRequest;
import com.whattoeattoday.recommendationservice.database.response.QueryTableNamesResponse;
import com.whattoeattoday.recommendationservice.database.response.QueryTableResponse;
import com.whattoeattoday.recommendationservice.database.service.DatabaseService;
import com.whattoeattoday.recommendationservice.database.service.TableService;
import com.whattoeattoday.recommendationservice.query.request.*;
import com.whattoeattoday.recommendationservice.query.service.api.QueryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/13/23
 */
@Service
public class QueryServiceImpl implements QueryService {

    @Resource
    private DatabaseService databaseService;

    @Resource
    private TableService tableService;

    @Override
    public BaseResponse queryCategoryInfo(QueryCategoryInfoRequest request) {
        String categoryName = request.getCategoryName();
        if (!databaseService.queryTableNames().tableNames.contains(categoryName)) {
            return BaseResponse.with(Status.NOT_FOUND, "Category Not Found");
        }
        QueryTableRequest queryTableRequest = new QueryTableRequest();
        queryTableRequest.setTableName(categoryName);
        QueryTableResponse response = databaseService.queryTable(queryTableRequest);
        return BaseResponse.with(Status.SUCCESS, response);
    }

    @Override
    public BaseResponse queryCategoryByName(QueryCategoryByNameRequest request) {
        if (ParamUtil.isBlank(request.getCategoryName()) || ParamUtil.isBlank(request.getPageNo()) || ParamUtil.isBlank(request.getPageSize())) {
            return BaseResponse.with(Status.PARAM_ERROR, "Param is Incomplete");
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

        QueryRowRequest queryRowRequest = QueryRowRequest.builder()
                .tableName(categoryName)
                .fieldNames(Arrays.asList(new String[]{"*"}))
                .conditionField("name")
                .conditionValue(request.getContentName())
                .pageInfo(pageInfo)
                .build();

        PageInfo queryResult = tableService.query(queryRowRequest);
        if (queryResult == null) {
            return BaseResponse.with(Status.FAILURE, "Database Execution Error");
        }
        return BaseResponse.with(Status.SUCCESS, queryResult);
    }

    @Override
    public BaseResponse queryAllCategoryName() {
        QueryTableNamesResponse response = databaseService.queryTableNames();
        if (response.tableNames == null || response.tableNames.isEmpty()) {
            return BaseResponse.with(Status.SUCCESS, "Database is Empty");
        }
        return BaseResponse.with(Status.SUCCESS, response.tableNames);
    }

    @Override
    public BaseResponse queryContentBySingleCondition(QueryContentBySingleConditionRequest request) {
        if (!ParamUtil.isAllNotBlank(new String[]{request.getCategoryName(), request.getConditionField(),
                request.getConditionValue(), request.pageNo, request.pageSize})) {
            return BaseResponse.with(Status.PARAM_ERROR, "Param is Incomplete");
        }
        if (!ParamUtil.isFiledNames(request.getFieldNames())) {
            return BaseResponse.with(Status.PARAM_ERROR, "Filed Name Error");
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

        QueryRowRequest queryRowRequest = QueryRowRequest.builder()
                .tableName(categoryName)
                .fieldNames(request.getFieldNames())
                .conditionField(request.getConditionField())
                .conditionValue(request.getConditionValue())
                .pageInfo(pageInfo)
                .build();

        PageInfo queryResult = tableService.query(queryRowRequest);
        return BaseResponse.with(Status.SUCCESS, queryResult);

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
