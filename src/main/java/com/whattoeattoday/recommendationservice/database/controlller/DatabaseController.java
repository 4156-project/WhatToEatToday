package com.whattoeattoday.recommendationservice.database.controlller;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.database.request.BuildTableRequest;
import com.whattoeattoday.recommendationservice.database.service.DatabaseService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/15/23
 */

@RestController
public class DatabaseController {

    @Resource
    private DatabaseService databaseService;

    public BaseResponse buildTable(@RequestBody BuildTableRequest request) {
        return databaseService.buildTable(request);
    }
}
