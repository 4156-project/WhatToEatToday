package com.whattoeattoday.recommendationservice.database.request.table;

import lombok.Data;

import java.util.List;

/**
 * @author Jiarong Shi js6132@columbia.edu
 * @date 10/15/23
 */
@Data
public class BuildTableRequest {
    public String tableName;
    public List<String> fieldNameList;
    public List<String> fieldTypeList;
    public String primaryKey;
    public String uniqueKey;
    public String autoIncrementField;
}
