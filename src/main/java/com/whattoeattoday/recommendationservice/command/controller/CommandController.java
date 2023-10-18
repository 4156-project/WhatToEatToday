package com.whattoeattoday.recommendationservice.command.controller;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.command.request.*;
import com.whattoeattoday.recommendationservice.command.service.api.CommandService;


import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
/**
 * @author Yufeng Wan
 */
@RestController
public class CommandController {
    @Resource
    public CommandService commandService;
    /**
     * insert an instance into a table
     */
    @PostMapping("/category/insert")
    public BaseResponse insert(@RequestBody InsertRequest request) {
        return commandService.insert(request);
    }
    /**
     * delete conditional instances from a table
     */
    @PostMapping("/category/delete")
    public BaseResponse delete(@RequestBody DeleteRequest request) {
        return commandService.delete(request);
    }

    /**
     * update information in a table
     */
    @PostMapping("/category/update")
    public BaseResponse update(@RequestBody UpdateRequest request) {
        return commandService.update(request);
    }

}
