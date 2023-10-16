package com.whattoeattoday.recommendationservice.database.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.util.Map;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/16/23
 */
@Data
public class QueryTableResponse {
    public String tableName;
    public Map<String, String> filedNameTypeMap;
    public Long rowNum;
    public BigInteger id;
}
