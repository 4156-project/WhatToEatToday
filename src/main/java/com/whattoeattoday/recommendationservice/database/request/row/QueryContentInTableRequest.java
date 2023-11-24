package com.whattoeattoday.recommendationservice.database.request.row;

import com.whattoeattoday.recommendationservice.common.PageInfo;
import lombok.Data;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 11/21/23
 */
@Data
public class QueryContentInTableRequest {
    PageInfo pageInfo;
    String tableName;
}
