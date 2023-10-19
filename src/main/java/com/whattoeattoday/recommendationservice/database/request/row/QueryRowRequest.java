package com.whattoeattoday.recommendationservice.database.request.row;

import com.whattoeattoday.recommendationservice.common.PageInfo;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Jiarong Shi js6132@columbia.edu
 * @date 10/15/23
 */
@Data
@Builder
public class QueryRowRequest {
    public String tableName;
    public List<String> fieldNames;
    public String conditionField;
    public String conditionValue;
    public PageInfo pageInfo;
}
