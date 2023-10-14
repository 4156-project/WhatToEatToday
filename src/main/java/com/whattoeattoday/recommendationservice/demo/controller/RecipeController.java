package com.whattoeattoday.recommendationservice.demo.controller;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.demo.request.QueryByIdRequest;
import com.whattoeattoday.recommendationservice.demo.service.api.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class RecipeController {
    @Resource
    private RecipeService recipeService;


    @PostMapping("/category/recipe/query/id")
    public BaseResponse queryById(@RequestBody QueryByIdRequest request) {
        return recipeService.queryById(request);
    }
}
