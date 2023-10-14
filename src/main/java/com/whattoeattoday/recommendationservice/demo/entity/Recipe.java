package com.whattoeattoday.recommendationservice.demo.entity;

import lombok.Data;

@Data
public class Recipe {
    private long id;
    private String title; // 名称
    private String description; // 描述
    private String createBy; // 创建人
    private String image; // 图片
}
