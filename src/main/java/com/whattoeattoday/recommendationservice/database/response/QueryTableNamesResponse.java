package com.whattoeattoday.recommendationservice.database.response;

import lombok.Data;

import java.util.Set;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/16/23
 */
@Data
public class QueryTableNamesResponse {
    public Set<String> tableNames;
}
