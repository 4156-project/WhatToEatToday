package com.whattoeattoday.recommendationservice.database.request.row;

import lombok.Data;

import java.util.List;

@Data
public class QueryRowRequest {
    public String tableName;
    public List<String> fieldNames;
    public String conditionField;
    public String conditionValue;
}
