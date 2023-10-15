package com.whattoeattoday.recommendationservice.database.service;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.database.request.BuildTableRequest;
import org.springframework.stereotype.Service;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/15/23
 */
public interface DatabaseService {
    public BaseResponse buildTable(BuildTableRequest request);
}
