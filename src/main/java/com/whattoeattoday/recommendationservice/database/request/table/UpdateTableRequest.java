package com.whattoeattoday.recommendationservice.database.request.table;

import lombok.Data;

@Data
public class UpdateTableRequest {
    String tableName;
    String columnName;
}
