package com.whattoeattoday.recommendationservice.demo.service.api;

import com.whattoeattoday.recommendationservice.common.BaseResponse;

public interface RecipeService {
    BaseResponse queryById(Long id);
}
