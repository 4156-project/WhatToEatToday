package com.whattoeattoday.recommendationservice.intertable.controller;


import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.database.request.table.BuildTableRequest;
import com.whattoeattoday.recommendationservice.database.request.table.DeleteTableRequest;
import com.whattoeattoday.recommendationservice.intertable.service.api.InterTableService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ruoxuanwang rw2961@columbia.edu
 * @Date 10/17/2023
 */
@RestController
public class InterTableController {

    @Resource
    public InterTableService intertableService;

    /**
     * Create a new table
     * @param request
     */
    @PostMapping("/category/create")
    public BaseResponse<Object> createTable(@RequestBody BuildTableRequest request) {
        return intertableService.createTable(request);
    }

    /**
     * Delete a table
     * @param request
     */
    @PostMapping("/category/delete")
    public BaseResponse<Object> deleteTable(@RequestBody DeleteTableRequest request) {
        return intertableService.deleteTable(request);
    }
}
