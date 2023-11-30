package com.whattoeattoday.recommendationservice.query.controller;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.PageInfo;
import com.whattoeattoday.recommendationservice.database.response.QueryTableResponse;
import com.whattoeattoday.recommendationservice.query.request.*;
import com.whattoeattoday.recommendationservice.query.service.api.QueryService;
import io.github.yedaxia.apidocs.Ignore;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @Date 10/13/2023
 */
@RestController
public class QueryController {

    @Resource
    public QueryService queryService;

    /**
     * Query basic information about a category
     * @param request
     */
    @PostMapping("/query/category/list/info")
    public BaseResponse<QueryTableResponse> queryCategoryInfo(@RequestBody QueryCategoryInfoRequest request) {
        return queryService.queryCategoryInfo(request);
    }

    /**
     * Query contents in a category
     * @param request
     */
    @PostMapping("/query/category/name")
    public BaseResponse<PageInfo> queryCategoryByName(@RequestBody QueryCategoryByNameRequest request) {
        return queryService.queryCategoryByName(request);
    }

    /**
     * Query names of all categories
     * @return
     */
    @GetMapping("/query/category/list/name")
    public BaseResponse<Set<String>> queryAllCategoryName() {
        return queryService.queryAllCategoryName();
    }

    /**
     * Query content in a given category
     * @param request
     * @return
     */
    @PostMapping("/query/content/")
    public BaseResponse<PageInfo> queryContent(@RequestBody QueryContentRequest request) {
        return queryService.queryContent(request);
    }

    /**
     * Query contents in a category with a single selection condition
     * @param request
     * @return
     */
    @PostMapping("/query/content/single-condition")
    public BaseResponse<PageInfo> queryContentBySingleCondition(@RequestBody QueryContentBySingleConditionRequest request) {
        return queryService.queryContentBySingleCondition(request);
    }

    @Ignore
    @PostMapping("/query/content/multi-condition")
    public BaseResponse queryContentByMultiCondition(@RequestBody QueryContentByMultiConditionRequest request) {
        return queryService.queryContentByMultiCondition(request);
    }

    @Ignore
    @PostMapping("/query/content/fuzzy")
    public BaseResponse fuzzySearchContent(@RequestBody FuzzySearchContentRequest request) {
        return queryService.fuzzySearchContent(request);
    }
}
