package com.whattoeattoday.recommendationservice.query.request;

import lombok.Data;

import java.util.List;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/14/23
 */
@Data
public class QueryContentByMultiConditionRequest {
    String categoryName;
    List<String> filedNameList;
    List<String> filedValueList;
}
