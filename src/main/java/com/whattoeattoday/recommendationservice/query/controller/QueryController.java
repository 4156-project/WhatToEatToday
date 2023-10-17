package com.whattoeattoday.recommendationservice.query.controller;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.query.request.*;
import com.whattoeattoday.recommendationservice.query.service.api.QueryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @Date 10/13/2023
 */
@RestController
public class QueryController {

    @Resource
    public QueryService queryService;

    @PostMapping("/query/category/list/info")
    public BaseResponse queryCategoryInfo(QueryCategoryInfoRequest request) {
        return queryService.queryCategoryInfo(request);
    }

    @PostMapping("/query/category/name")
    public BaseResponse queryCategoryByName(@RequestBody QueryCategoryByNameRequest request) {
        return queryService.queryCategoryByName(request);
    }

    @GetMapping("/query/category/list/name")
    public BaseResponse queryAllCategoryName() {
        return queryService.queryAllCategoryName();
    }

    @PostMapping("/query/content/single-condition")
    public BaseResponse queryContentBySingleCondition(@RequestBody QueryContentBySingleConditionRequest request) {
        return queryService.queryContentBySingleCondition(request);
    }

    @PostMapping("/query/content/multi-condition")
    public BaseResponse queryContentByMultiCondition(@RequestBody QueryContentByMultiConditionRequest request) {
        return queryService.queryContentByMultiCondition(request);
    }

    @PostMapping("/query/content/fuzzy")
    public BaseResponse fuzzySearchContent(@RequestBody FuzzySearchContentRequest request) {
        return queryService.fuzzySearchContent(request);
    }
}
