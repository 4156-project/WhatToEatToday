package com.whattoeattoday.recommendationservice.query.controller;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.query.request.QueryByNameRequest;
import com.whattoeattoday.recommendationservice.query.service.api.QueryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
