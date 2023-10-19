package com.whattoeattoday.recommendationservice.intratable.controller;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.intratable.request.*;
import com.whattoeattoday.recommendationservice.intratable.service.api.IntraTableService;


import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
/**
 * @author Yufeng Wan
 */
@RestController
public class IntraTableController {
    @Resource
    public IntraTableService intraTableService;
    /**
     * insert a content into a category
     * @param request
     */
    @PostMapping("/content/insert")
    public BaseResponse insert(@RequestBody InsertRequest request) {
        return intraTableService.insert(request);
    }
    /**
     * delete contents from a category by setting conditions
     * @param request
     */
    @PostMapping("/content/delete")
    public BaseResponse delete(@RequestBody DeleteRequest request) {
        return intraTableService.delete(request);
    }

    /**
     * Modify contents in a category
     * @param request
     */
    @PostMapping("/content/update")
    public BaseResponse update(@RequestBody UpdateRequest request) {
        return intraTableService.update(request);
    }

}
