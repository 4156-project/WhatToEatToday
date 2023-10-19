package com.whattoeattoday.recommendationservice.database.request.row;

import lombok.Data;

/**
 * @author Jiarong Shi js6132@columbia.edu
 * @date 10/15/23
 */
@Data
public class DeleteRowRequest {
    public String tableName;
    public String conditionField;
    public String conditionValue;
}
