package com.whattoeattoday.recommendationservice.command.request;
import lombok.Data;
import java.util.Map;

/**
 * @author Yufeng Wan yw3921@columbia.edu
 * @date 10/15/23
 */
@Data
public class InsertRequest {
    String tableName;
    Map<String, Object>content;
}
