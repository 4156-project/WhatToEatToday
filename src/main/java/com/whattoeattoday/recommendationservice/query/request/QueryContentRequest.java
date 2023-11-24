package com.whattoeattoday.recommendationservice.query.request;

import lombok.Data;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 11/21/23
 */
@Data
public class QueryContentRequest {
    public String categoryName;
    public String pageNo;
    public String pageSize;
}
