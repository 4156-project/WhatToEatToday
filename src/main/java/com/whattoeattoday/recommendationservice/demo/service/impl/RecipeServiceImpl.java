package com.whattoeattoday.recommendationservice.demo.service.impl;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.ParamUtil;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.demo.dao.RecipeMapper;
import com.whattoeattoday.recommendationservice.demo.entity.Recipe;
import com.whattoeattoday.recommendationservice.demo.request.QueryByIdRequest;
import com.whattoeattoday.recommendationservice.demo.service.api.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    @Resource
    private RecipeMapper recipeMapper;
    @Override
    public BaseResponse queryById(QueryByIdRequest request) {
        if (ParamUtil.isBlank(request.getId())) {
            return BaseResponse.with(Status.PARAM_ERROR);
        }
        Long id = Long.valueOf(request.getId());
        log.info("The input id is: {}", id);
        if (id == null) {
            return BaseResponse.with(Status.PARAM_ERROR);
        }
        Recipe recipe = recipeMapper.queryById(id);
        return BaseResponse.with(Status.SUCCESS, recipe);
    }
}
