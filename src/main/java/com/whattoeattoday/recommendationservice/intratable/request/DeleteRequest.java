package com.whattoeattoday.recommendationservice.intratable.request;
import lombok.Data;

/**
 * @author Yufeng Wan yw3921@columbia.edu
 * @date 10/15/23
 */
@Data
public class DeleteRequest {
    public String tableName;
    public String conditionField;
    public Object conditionValue;
}
