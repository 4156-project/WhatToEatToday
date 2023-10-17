package com.whattoeattoday.recommendationservice.database.request.row;

import lombok.Data;

import java.util.List;

@Data
public class InsertRowRequest {
    public String tableName;
    public List<String> filedNames;
    public List<String> values;
}
