package com.whattoeattoday.recommendationservice.database.request;

import lombok.Data;

import java.util.List;

@Data
public class BuildTableRequest {
    public String tableName;
    public List<String> fieldNameList;
    public List<String> fieldTypeList;
    public String primaryKey;
}
