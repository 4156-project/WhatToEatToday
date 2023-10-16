package com.whattoeattoday.recommendationservice.common;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.Map;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/15/23
 */
@Data
@Builder
public class PageInfo {
    @NonNull
    Integer pageNo;
    @NonNull
    Integer pageSize;
    List<Map<String,Object>> pageData;
}
