package com.whattoeattoday.recommendationservice.database.request.row;

import lombok.Data;

import java.util.List;

@Data
public class UpdateRowRequest {
    public String tableName;
    public List<String> filedNames;
    public List<String> values;
    public String conditionField;
    public String conditionValue;
}
