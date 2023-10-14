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
    @PostMapping("/category/query/name")
    public BaseResponse queryByName(@RequestBody QueryByNameRequest request) {
        return queryService.queryByName(request);
    }

    @PostMapping("/query/category/name")
    public BaseResponse queryCategoryByName(@RequestBody QueryCategoryByNameRequest request) {
        return queryService.queryCategoryByName(request);
    }

    @GetMapping("/query/category/list")
    public BaseResponse queryAllCategory() {
        return queryService.queryAllCategory();
    }

    public BaseResponse queryContentByName(@RequestBody QueryContentByNameRequest request) {
        return queryService.queryContentByName(request);
    }

    public BaseResponse queryContentByMultiCondition(@RequestBody QueryContentByMultiConditionRequest request) {
        return queryService.queryContentByMultiCondition(request);
    }

    public BaseResponse fuzzySearchContent(@RequestBody FuzzySearchContentRequest request) {
        return queryService.fuzzySearchContent(request);
    }
}
