package com.whattoeattoday.recommendationservice;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.database.request.table.BuildTableRequest;
import com.whattoeattoday.recommendationservice.database.request.table.DeleteTableRequest;
import com.whattoeattoday.recommendationservice.database.request.table.QueryTableRequest;
import com.whattoeattoday.recommendationservice.database.request.table.UpdateTableRequest;
import com.whattoeattoday.recommendationservice.database.response.QueryTableNamesResponse;
import com.whattoeattoday.recommendationservice.database.response.QueryTableResponse;
import com.whattoeattoday.recommendationservice.database.service.DatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecommendationServiceApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DatabaseService databaseService;

    @Test
    public void contextLoads() throws SQLException {
        Connection connection = dataSource.getConnection();
        log.info("Connection: {}", connection);
        connection.close();
    }

    @Test
    public void test0BuildTable() {
        BuildTableRequest request = new BuildTableRequest();

        List<String> fieldNameList = new ArrayList<>();
        fieldNameList.add("id");
        fieldNameList.add("username");
        fieldNameList.add("password");
        fieldNameList.add("email");
        fieldNameList.add("category");
        List<String> fieldTypeList = new ArrayList<>();
        fieldTypeList.add("BIGINT(20)");
        fieldTypeList.add("VARCHAR(50)");
        fieldTypeList.add("VARCHAR(50)");
        fieldTypeList.add("VARCHAR(50)");
        fieldTypeList.add("VARCHAR(50)");

        request.setTableName("test1126");
        request.setFieldNameList(fieldNameList);
        request.setFieldTypeList(fieldTypeList);
        request.setPrimaryKey("id");
        request.setAutoIncrementField("id");
        request.setUniqueKey("username");
        // test build success
        BaseResponse response = databaseService.buildTable(request);
        log.info("RESPONSE: {}", response);
        Assert.assertTrue(response.isSuccess());

        request.setUniqueKey("title");
        response = databaseService.buildTable(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
    }

    @Test
    public void test1SetAutoIncrement() {
        UpdateTableRequest request = new UpdateTableRequest();
        request.setTableName("test1126");
        request.setColumnName("id");
        BaseResponse response = databaseService.setAutoIncrement(request);
        log.info("RESPONSE: {}", response);
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void test2SetUniqueKey() {
        UpdateTableRequest request = new UpdateTableRequest();
        request.setTableName("test1126");
        request.setColumnName("name");
        BaseResponse response = databaseService.setUniqueKey(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.PARAM_ERROR);
    }
    @Test
    public void test5DeleteTable() {
        DeleteTableRequest request = new DeleteTableRequest();
        request.setTableName("test1126");
        // test success
        BaseResponse response = databaseService.deleteTable(request);
        log.info("RESPONSE: {}", response);
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void test3QueryTable() {
        QueryTableRequest request = new QueryTableRequest();
        request.setTableName("test1126");
        QueryTableResponse response = databaseService.queryTable(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getTableName(), request.getTableName());
    }

    @Test
    public void test4QueryTableNames() {
        QueryTableNamesResponse response = databaseService.queryTableNames();
        log.info("RESPONSE: {}", response);
        Assert.assertFalse(response.tableNames.isEmpty());
    }
}
