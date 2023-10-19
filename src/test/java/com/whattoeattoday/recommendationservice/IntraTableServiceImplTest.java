package com.whattoeattoday.recommendationservice;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.intratable.request.DeleteRequest;
import com.whattoeattoday.recommendationservice.intratable.request.InsertRequest;
import com.whattoeattoday.recommendationservice.intratable.request.UpdateRequest;
import com.whattoeattoday.recommendationservice.intratable.service.api.IntraTableService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
/**
 * @author Yufeng Wan yw3921@columbia.edu
 * @date 10/16/23
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecommendationServiceApplication.class)
public class IntraTableServiceImplTest {
    @Resource
    private IntraTableService intraTableService;

    @Test
    @Order(1)
    public void testInsert(){
        InsertRequest request = new InsertRequest();
        request.setTableName("test1016");
        Map<String, Object> fieldNameValues = new HashMap<>();
        fieldNameValues.put("name", "Lee");
        fieldNameValues.put("gender","Male");
        fieldNameValues.put("age", 2);
        request.setFieldNameValues(fieldNameValues);
        BaseResponse response = intraTableService.insert(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.SUCCESS);
    }

    @Test
    @Order(3)
    public void testDelete(){
        DeleteRequest request = new DeleteRequest();
        request.setTableName("test1016");
        request.setConditionField("name");
        request.setConditionValue("Lee");
        BaseResponse response = intraTableService.delete(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.SUCCESS);
    }

    @Test
    @Order(2)
    public void testUpdate(){
        UpdateRequest request = new UpdateRequest();
        request.setTableName("test1016");
        request.setConditionField("name");
        request.setConditionValue("Lee");
        Map<String,Object>map = new HashMap<>();
        map.put("gender","Male");
        map.put("age",6);
        request.setFieldNameValues(map);
        BaseResponse response = intraTableService.update(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.SUCCESS);
    }
}
