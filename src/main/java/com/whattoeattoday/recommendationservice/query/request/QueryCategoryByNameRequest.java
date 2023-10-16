package com.whattoeattoday.recommendationservice.query.request;

import lombok.Data;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/14/23
 */

@Data
public class QueryCategoryByNameRequest {
    String categoryName;
    String pageNo;
    String pageSize;
}
