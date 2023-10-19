package com.whattoeattoday.recommendationservice.database.request.row;

import lombok.Data;

import java.util.List;

/**
 * @author Jiarong Shi js6132@columbia.edu
 * @date 10/15/23
 */
@Data
public class UpdateRowRequest {
    public String tableName;
    public List<String> filedNames;
    public List<String> values;
    public String conditionField;
    public String conditionValue;
}
