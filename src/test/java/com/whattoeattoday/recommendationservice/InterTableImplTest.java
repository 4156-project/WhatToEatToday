package com.whattoeattoday.recommendationservice;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.database.request.table.BuildTableRequest;
import com.whattoeattoday.recommendationservice.database.request.table.DeleteTableRequest;
import com.whattoeattoday.recommendationservice.intertable.service.api.InterTableService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruoxuan Wang
 * @date
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecommendationServiceApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InterTableImplTest {
    @Resource
    private InterTableService interTableService;

    @Test
    public void Test0CreateTable() {
        //Test 1
        BuildTableRequest request = new BuildTableRequest();
        request.setTableName("book1");
        List<String> fieldNameList = new ArrayList<>();
        fieldNameList.add("id");
        fieldNameList.add("title");
        request.setFieldNameList(fieldNameList);
        List<String> fieldTypeList = new ArrayList<>();
        fieldTypeList.add("INT");
        fieldTypeList.add("VARCHAR(20)");
        request.setFieldTypeList(fieldTypeList);
        request.setPrimaryKey("id");
        BaseResponse response = interTableService.createTable(request);
        log.info("TestCreateTable RESPONSE1: {}", response);
        Assert.assertEquals(response.getCode(), Status.SUCCESS);
        Assert.assertEquals("Table book1 is created", response.getMessage());
        //Test 2: add unique key and auto increment
        request.setTableName("book2");
        request.setAutoIncrementField("id");
        request.setUniqueKey("title");
        response = interTableService.createTable(request);
        log.info("TestCreateTable RESPONSE2: {}", response);
        Assert.assertEquals(response.getCode(), Status.SUCCESS);
        Assert.assertEquals("Table book2 is created", response.getMessage());
        //Test 3: no table name
        request.setTableName(null);
        response = interTableService.createTable(request);
        log.info("TestCreateTable RESPONSE3: {}", response);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
        Assert.assertEquals("Param is Incomplete",response.getMessage());
        //Test 4: empty field name
        request.setTableName("book3");
        request.setFieldNameList(null);
        response = interTableService.createTable(request);
        log.info("TestCreateTable RESPONSE4: {}", response);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
        Assert.assertEquals("No Field",response.getMessage());
        //Test 5: empty string in field list
        fieldNameList.add("");
        request.setFieldNameList(fieldNameList);
        response = interTableService.createTable(request);
        log.info("TestCreateTable RESPONSE5: {}", response);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
        Assert.assertEquals("FieldNameList is Incomplete",response.getMessage());
        //Test 6: empty field name
        fieldNameList.remove(fieldNameList.size()-1);
        request.setFieldNameList(fieldNameList);
        request.setFieldTypeList(null);
        response = interTableService.createTable(request);
        log.info("TestCreateTable RESPONSE6: {}", response);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
        Assert.assertEquals("No Type",response.getMessage());
        //Test 7: empty string in field list
        fieldTypeList.add("");
        request.setFieldTypeList(fieldTypeList);
        response = interTableService.createTable(request);
        log.info("TestCreateTable RESPONSE7: {}", response);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
        Assert.assertEquals("FieldTypeList is Incomplete",response.getMessage());
        fieldTypeList.remove(fieldTypeList.size()-1);
        request.setFieldTypeList(fieldTypeList);
        //Test 8: table existed
        request.setTableName("book1");
        response = interTableService.createTable(request);
        log.info("TestCreateTable RESPONSE8: {}", response);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
        Assert.assertEquals("Table book1 Already Created", response.getMessage());
        //Test 9: invalid primary key
        request.setTableName("book9");
        request.setPrimaryKey("false");
        response = interTableService.createTable(request);
        log.info("TestCreateTable RESPONSE9: {}", response);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
        Assert.assertEquals("Invalid Primary Key false", response.getMessage());
        request.setPrimaryKey("id");
        //Test 10: unequal length of field name and type
        fieldNameList.add("author");
        request.setFieldNameList(fieldNameList);
        response = interTableService.createTable(request);
        log.info("TestCreateTable RESPONSE10: {}", response);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
        Assert.assertEquals("Unequal Length of Field Name and Field Type", response.getMessage());
        fieldNameList.remove(fieldNameList.size()-1);
        request.setFieldNameList(fieldNameList);
        //Test11: invalid field type
        fieldTypeList.remove(fieldTypeList.size()-1);
        fieldTypeList.add("VARCHAR");
        request.setFieldTypeList(fieldTypeList);
        response = interTableService.createTable(request);
        log.info("TestCreateTable RESPONSE11: {}", response);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
        Assert.assertEquals("Invalid Field Type: VARCHAR", response.getMessage());
        fieldTypeList.remove(fieldTypeList.size()-1);
        fieldTypeList.add("VARCHAR(20)");
        request.setFieldTypeList(fieldTypeList);
        //Test12: invalid unique key
        request.setUniqueKey("false");
        response = interTableService.createTable(request);
        log.info("TestCreateTable RESPONSE12: {}", response);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
        Assert.assertEquals("Invalid Unique Key false", response.getMessage());
        request.setUniqueKey("title");
        //Test13: invalid auto increment field
        request.setAutoIncrementField("false");
        response = interTableService.createTable(request);
        log.info("TestCreateTable RESPONSE12: {}", response);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
        Assert.assertEquals("Invalid Auto Increment Field false", response.getMessage());
        request.setAutoIncrementField("id");
    }

    @Test
    public void Test1DeleteTable() {
        DeleteTableRequest request = new DeleteTableRequest();
        //Test Exist
        request.setTableName("book1");
        BaseResponse response = interTableService.deleteTable(request);
        log.info("TestDeleteTable RESPONSE1: {}", response);
        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals("Table book1 is deleted", response.getMessage());
        request.setTableName("book2");
        response = interTableService.deleteTable(request);
        log.info("TestDeleteTable RESPONSE2: {}", response);
        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals("Table book2 is deleted", response.getMessage());
        //Test Not Exist
        request.setTableName("notExists");
        response = interTableService.deleteTable(request);
        log.info("TestDeleteTable RESPONSE3: {}", response);
        Assert.assertFalse(response.isSuccess());
    }
}
