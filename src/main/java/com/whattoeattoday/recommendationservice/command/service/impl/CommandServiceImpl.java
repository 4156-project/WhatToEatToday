package com.whattoeattoday.recommendationservice.command.service.impl;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.command.request.*;
import com.whattoeattoday.recommendationservice.command.service.api.CommandService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
/**
 * @author Yufeng Wan yw3921@columbia.edu
 * @date 10/16/23
 */
@Service
public class CommandServiceImpl implements CommandService{
    @Override
    public BaseResponse Insert(InsertRequest request) {
        //TODO: use other service to insert an instance into a table
        return null;
    }
    @Override
    public BaseResponse Delete(DeleteRequest request) {
        //TODO: use other service to delete an instance into a table
        return null;
    }

    @Override
    public BaseResponse Update(UpdateRequest request) {
        //TODO: use other service to update an instance into a table
        return null;
    }
}
