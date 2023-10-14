package com.whattoeattoday.recommendationservice.demo.dao;

import com.whattoeattoday.recommendationservice.demo.entity.Recipe;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RecipeMapper {
    @Select("select * from recipes")
    List<Recipe> findAll();

    Recipe queryById(@Param("id") long id);
}
