package com.whattoeattoday.recommendationservice.database.request;

import lombok.Data;

import java.util.List;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/15/23
 */

@Data
public class BuildTableRequest {
    public String tableName;
    public List<String> fieldNameList;
    public List<String> fieldTypeList;
    public String primaryKey;
}
