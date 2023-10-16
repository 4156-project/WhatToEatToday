package com.whattoeattoday.recommendationservice.command.controller;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.command.request.*;
import com.whattoeattoday.recommendationservice.command.service.api.CommandService;


import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
@RestController
public class CommandController {
    @Resource
    public CommandService commandService;

    @PostMapping("/table/insert")
    public BaseResponse Insert(@RequestBody InsertRequest request) {
        return commandService.Insert(request);
    }
    @PostMapping("/table/delete")
    public BaseResponse Delete(@RequestBody DeleteRequest request) {
        return commandService.Delete(request);
    }

    @PostMapping("/table/update")
    public BaseResponse Update(@RequestBody UpdateRequest request) {
        return commandService.Update(request);
    }

}
