package com.whattoeattoday.recommendationservice.demo.controller;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.demo.service.api.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class RecipeController {
    @Resource
    private RecipeService recipeService;

    @ResponseBody
    @RequestMapping("/category/recipe/query")
    public BaseResponse queryById(@RequestParam Long id) {
        BaseResponse baseResponse = recipeService.queryById(id);
        return baseResponse;
    }
}
