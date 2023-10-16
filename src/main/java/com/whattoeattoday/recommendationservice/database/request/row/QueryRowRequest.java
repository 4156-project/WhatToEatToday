package com.whattoeattoday.recommendationservice.database.request.row;

import com.whattoeattoday.recommendationservice.common.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class QueryRowRequest {
    public String tableName;
    public List<String> fieldNames;
    public String conditionField;
    public String conditionValue;
    public PageInfo pageInfo;
}
