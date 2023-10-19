package com.whattoeattoday.recommendationservice.database.response;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Jiarong Shi js6132@columbia.edu
 * @date 10/15/23
 */
@Data
public class QueryRowResponse {
    public List<Map<String, Object>> rows;
}
