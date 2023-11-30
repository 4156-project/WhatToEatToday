package com.whattoeattoday.recommendationservice.database.request.row;

import lombok.Data;

import java.util.List;

/**
 * @author Jiarong Shi js6132@columbia.edu
 * @date 11/30/23
 */
@Data
public class DeleteRowPlusRequest {
    public String tableName;
    public List<String> conditionFields;
    public List<String> conditionValues;
}
