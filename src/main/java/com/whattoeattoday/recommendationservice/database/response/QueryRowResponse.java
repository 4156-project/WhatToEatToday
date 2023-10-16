package com.whattoeattoday.recommendationservice.database.response;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class QueryRowResponse {
    public List<Map<String, Object>> rows;
}
