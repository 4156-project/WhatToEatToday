package com.whattoeattoday.recommendationservice.command.request;
import lombok.Data;
import java.util.List;

/**
 * @author Yufeng Wan yw3921@columbia.edu
 * @date 10/15/23
 */
@Data
public class UpdateRequest {
    public String tableName;
    public List<String> fieldNames;
    public List<String> values;
    public String conditionField;
    public String conditionValue;
}
