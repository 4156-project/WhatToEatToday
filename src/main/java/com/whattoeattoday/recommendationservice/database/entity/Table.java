package com.whattoeattoday.recommendationservice.database.entity;

import lombok.Data;

import java.util.List;

@Data
public class Table {
    private String name;
    private int rowNum;
    private List<String> columnNames;
    private List<String> columnTypes;
}
