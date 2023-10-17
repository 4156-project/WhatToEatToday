package com.whattoeattoday.recommendationservice;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.PageInfo;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.command.request.*;
import com.whattoeattoday.recommendationservice.command.service.api.CommandService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
/**
 * @author Yufeng Wan yw3921@columbia.edu
 * @date 10/16/23
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecommendationServiceApplication.class)
public class CommandServiceImplTest {
    @Resource
    private CommandService commandService;

    @Test
    public void TestInsert(){
        InsertRequest request = new InsertRequest();
        request.setTableName("table");
        Map<String, Object> fieldNameValues = new HashMap<>();
        fieldNameValues.put("food", 1);
        fieldNameValues.put("drink", 2);
        request.setFieldNameValues(fieldNameValues);
        BaseResponse response = commandService.Insert(request);
        Assert.assertEquals(response.getCode(), Status.SUCCESS);
    }
}
