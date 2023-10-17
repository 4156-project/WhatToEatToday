package com.whattoeattoday.recommendationservice.query.request;

import lombok.Data;

import java.util.List;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/14/23
 */

@Data
public class QueryContentBySingleConditionRequest {
    public String categoryName;
    public List<String> fieldNames;
    public String conditionField;
    public String conditionValue;
    public String pageNo;
    public String pageSize;
}
