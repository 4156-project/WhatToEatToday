package com.whattoeattoday.recommendationservice.demo.service.api;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.demo.request.QueryByIdRequest;

public interface RecipeService {
    BaseResponse queryById(QueryByIdRequest request);
}
