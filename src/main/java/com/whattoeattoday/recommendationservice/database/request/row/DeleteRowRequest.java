package com.whattoeattoday.recommendationservice.database.request.row;

import lombok.Data;

@Data
public class DeleteRowRequest {
    public String tableName;
    public String conditionField;
    public String conditionValue;
}
