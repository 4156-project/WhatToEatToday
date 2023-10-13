package com.whattoeattoday.recommendationservice.query.controller;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.request.CategoryQueryRequest;
import com.whattoeattoday.recommendationservice.request.PrintDetailRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @Date 10/13/2023
 */
@RestController
public class QueryController {

    @PostMapping("/category/query")
    public BaseResponse categoryQuery(@RequestBody CategoryQueryRequest request) {
        return null;
    }
}
