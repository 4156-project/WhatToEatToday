package com.whattoeattoday.recommendationservice;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.intratable.request.DeleteRequest;
import com.whattoeattoday.recommendationservice.intratable.request.InsertRequest;
import com.whattoeattoday.recommendationservice.intratable.request.UpdateRequest;
import com.whattoeattoday.recommendationservice.intratable.service.api.IntraTableService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntraTableServiceImplTest {
    @Resource
    private IntraTableService intraTableService;

    @Test
    public void test0Insert(){
        InsertRequest request = new InsertRequest();
        request.setUsername("Larry");
        request.setPassword("12345");
        request.setTableName("food");
        Map<String, Object> fieldNameValues = new HashMap<>();
        fieldNameValues.put("title", "testfood");
        fieldNameValues.put("ingredients","Water");
        request.setFieldNameValues(fieldNameValues);
        BaseResponse response = intraTableService.insert(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.SUCCESS);
    }

    @Test
    public void test2Delete(){
        DeleteRequest request = new DeleteRequest();
        request.setUsername("Larry");
        request.setPassword("12345");
        request.setTableName("food");
        request.setConditionField("title");
        request.setConditionValue("updatefood");
        BaseResponse response = intraTableService.delete(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.SUCCESS);
    }

    @Test
    public void test1Update(){
        UpdateRequest request = new UpdateRequest();
        request.setUsername("Larry");
        request.setPassword("12345");
        request.setTableName("food");
        request.setConditionField("title");
        request.setConditionValue("testfood");
        Map<String,Object>map = new HashMap<>();
        map.put("title","updatefood");
        request.setFieldNameValues(map);
        BaseResponse response = intraTableService.update(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.SUCCESS);
    }
    @Test
    public void testUser0Insert(){
        InsertRequest request = new InsertRequest();
        request.setUsername("Larry");
        request.setPassword("12345");
        request.setTableName("user");
        Map<String, Object> fieldNameValues = new HashMap<>();
        fieldNameValues.put("title", "testfood");
        fieldNameValues.put("ingredients","Water");
        request.setFieldNameValues(fieldNameValues);
        BaseResponse response = intraTableService.insert(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
    }

    @Test
    public void testUser2Delete(){
        DeleteRequest request = new DeleteRequest();
        request.setUsername("Larry");
        request.setPassword("12345");
        request.setTableName("user");
        request.setConditionField("title");
        request.setConditionValue("updatefood");
        BaseResponse response = intraTableService.delete(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
    }

    @Test
    public void testUser1Update(){
        UpdateRequest request = new UpdateRequest();
        request.setUsername("Larry");
        request.setPassword("12345");
        request.setTableName("user");
        request.setConditionField("title");
        request.setConditionValue("testfood");
        Map<String,Object>map = new HashMap<>();
        map.put("title","updatefood");
        request.setFieldNameValues(map);
        BaseResponse response = intraTableService.update(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
    }

    @Test
    public void testInsertF1(){
        InsertRequest request = new InsertRequest();
        request.setUsername("Larry");
        request.setPassword("12345");
        request.setTableName("testfault");
        Map<String, Object> fieldNameValues = new HashMap<>();
        fieldNameValues.put("name", "Lee");
        fieldNameValues.put("gender","Male");
        fieldNameValues.put("age", 2);
        request.setFieldNameValues(fieldNameValues);
        BaseResponse response = intraTableService.insert(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
    }

    @Test
    public void testInsertF2(){
        InsertRequest request = new InsertRequest();
        request.setUsername("Larry");
        request.setPassword("12345");
        request.setTableName("food");
        Map<String, Object> fieldNameValues = new HashMap<>();
        fieldNameValues.put("n", "Lee");
        fieldNameValues.put("gender","Male");
        fieldNameValues.put("age", 2);
        request.setFieldNameValues(fieldNameValues);
        BaseResponse response = intraTableService.insert(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
    }

    @Test
    public void testInsertF3(){
        InsertRequest request = new InsertRequest();
        request.setUsername("Larry");
        request.setPassword("12345");
        request.setTableName("food");
        Map<String, Object> fieldNameValues = new HashMap<>();
        fieldNameValues.put("title", 1);
        request.setFieldNameValues(fieldNameValues);
        BaseResponse response = intraTableService.insert(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
    }

    @Test
    public void testDeleteF1(){
        DeleteRequest request = new DeleteRequest();
        request.setUsername("Larry");
        request.setPassword("12345");
        request.setTableName("testF");
        request.setConditionField("name");
        request.setConditionValue("Lee");
        BaseResponse response = intraTableService.delete(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
    }
    @Test
    public void testDeleteF2(){
        DeleteRequest request = new DeleteRequest();
        request.setUsername("Larry");
        request.setPassword("12345");
        request.setTableName("food");
        request.setConditionField("");
        request.setConditionValue("Lee");
        BaseResponse response = intraTableService.delete(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
    }

    @Test
    public void testUpdateF1(){
        UpdateRequest request = new UpdateRequest();
        request.setUsername("Larry");
        request.setPassword("12345");
        request.setTableName("testF");
        request.setConditionField("name");
        request.setConditionValue("Lee");
        Map<String,Object>map = new HashMap<>();
        map.put("gender","Male");
        map.put("age",6);
        request.setFieldNameValues(map);
        BaseResponse response = intraTableService.update(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
    }

    @Test
    public void testUpdateF2(){
        UpdateRequest request = new UpdateRequest();
        request.setUsername("Larry");
        request.setPassword("12345");
        request.setTableName("food");
        request.setConditionField("");
        request.setConditionValue("Lee");
        Map<String,Object>map = new HashMap<>();
        map.put("gender","Male");
        map.put("age",6);
        request.setFieldNameValues(map);
        BaseResponse response = intraTableService.update(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
    }

    @Test
    public void testUpdateF3(){
        UpdateRequest request = new UpdateRequest();
        request.setUsername("Larry");
        request.setPassword("12345");
        request.setTableName("food");
        request.setConditionField("title");
        request.setConditionValue("Plum-Glazed Turkey");
        Map<String,Object>map = new HashMap<>();
        map.put("g","Male");
        request.setFieldNameValues(map);
        BaseResponse response = intraTableService.update(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
    }

    @Test
    public void testUpdateF4(){
        UpdateRequest request = new UpdateRequest();
        request.setUsername("Larry");
        request.setPassword("12345");
        request.setTableName("food");
        request.setConditionField("title");
        request.setConditionValue("Plum-Glazed Turkey");
        Map<String,Object>map = new HashMap<>();
        map.put("title",123);
        request.setFieldNameValues(map);
        BaseResponse response = intraTableService.update(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
    }
    @Test
    public void testInsertWronguser(){
        InsertRequest request = new InsertRequest();
        request.setUsername("L");
        request.setPassword("12345");
        request.setTableName("food");
        Map<String, Object> fieldNameValues = new HashMap<>();
        fieldNameValues.put("title", "testfood");
        fieldNameValues.put("ingredients","Water");
        request.setFieldNameValues(fieldNameValues);
        BaseResponse response = intraTableService.insert(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
    }
    @Test
    public void test2eleteWrongUser(){
        DeleteRequest request = new DeleteRequest();
        request.setUsername("L");
        request.setPassword("12345");
        request.setTableName("food");
        request.setConditionField("title");
        request.setConditionValue("updatefood");
        BaseResponse response = intraTableService.delete(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
    }
    @Test
    public void testUpdateWrongUser(){
        UpdateRequest request = new UpdateRequest();
        request.setUsername("L");
        request.setPassword("12345");
        request.setTableName("food");
        request.setConditionField("title");
        request.setConditionValue("testfood");
        Map<String,Object>map = new HashMap<>();
        map.put("title","updatefood");
        request.setFieldNameValues(map);
        BaseResponse response = intraTableService.update(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
    }
}
