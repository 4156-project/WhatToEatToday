package com.whattoeattoday.recommendationservice;

import com.whattoeattoday.recommendationservice.demo.dao.RecipeMapper;
import com.whattoeattoday.recommendationservice.demo.entity.Recipe;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class RecommendationServiceApplicationTests {

    @Resource
    private RecipeMapper recipeMapper;
    @Test
    void contextLoads() {
    }

    @Test
    public void testFindAll() {
        List<Recipe> list = recipeMapper.findAll();
        System.out.println(list);
    }
}
