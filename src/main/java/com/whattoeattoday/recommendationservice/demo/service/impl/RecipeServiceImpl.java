package com.whattoeattoday.recommendationservice.demo.service.impl;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.demo.dao.RecipeMapper;
import com.whattoeattoday.recommendationservice.demo.entity.Recipe;
import com.whattoeattoday.recommendationservice.demo.service.api.RecipeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Resource
    private RecipeMapper recipeMapper;
    @Override
    public BaseResponse queryById(Long id) {
        if (id == null) {
            return BaseResponse.with(Status.PARAM_ERROR);
        }
        Recipe recipe = recipeMapper.queryById(id);
        return BaseResponse.with(Status.SUCCESS, recipe);
    }
}
