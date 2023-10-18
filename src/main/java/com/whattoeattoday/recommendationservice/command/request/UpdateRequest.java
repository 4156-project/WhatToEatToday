package com.whattoeattoday.recommendationservice.command.request;
import lombok.Data;
import java.util.Map;

/**
 * @author Yufeng Wan yw3921@columbia.edu
 * @date 10/15/23
 */
@Data
public class UpdateRequest {
    public String tableName;
    public Map<String,Object> fieldNameValues;
    public String conditionField;
    public Object conditionValue;
}
