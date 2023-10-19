package com.whattoeattoday.recommendationservice;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.database.request.table.BuildTableRequest;
import com.whattoeattoday.recommendationservice.database.request.table.DeleteTableRequest;
import com.whattoeattoday.recommendationservice.database.request.table.QueryTableRequest;
import com.whattoeattoday.recommendationservice.database.request.table.UpdateTableRequest;
import com.whattoeattoday.recommendationservice.database.response.QueryTableNamesResponse;
import com.whattoeattoday.recommendationservice.database.response.QueryTableResponse;
import com.whattoeattoday.recommendationservice.database.service.DatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    public void testBuildTable() {
        BuildTableRequest request = new BuildTableRequest();

        List<String> fieldNameList = new ArrayList<>();
        fieldNameList.add("id");
        fieldNameList.add("title");
        fieldNameList.add("description");
        List<String> fieldTypeList = new ArrayList<>();
        fieldTypeList.add("BIGINT(20)");
        fieldTypeList.add("VARCHAR(20)");
        fieldTypeList.add("TEXT");

        request.setTableName("test1017");
        request.setFieldNameList(fieldNameList);
        request.setFieldTypeList(fieldTypeList);
        request.setPrimaryKey("id");
        request.setUniqueKey("title");
        request.setAutoIncrementField("id");

        BaseResponse response = databaseService.buildTable(request);
        log.info("RESPONSE: {}", response);
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void testSetAutoIncrement() {
        UpdateTableRequest request = new UpdateTableRequest();
        request.setTableName("test1016");
        request.setColumnName("id");
        BaseResponse response = databaseService.setAutoIncrement(request);
        log.info("RESPONSE: {}", response);
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void testSetUniqueKey() {
        UpdateTableRequest request = new UpdateTableRequest();
        request.setTableName("test1016");
        request.setColumnName("name");
        BaseResponse response = databaseService.setUniqueKey(request);
        log.info("RESPONSE: {}", response);
        Assert.assertTrue(response.isSuccess());
    }
    @Test
    public void testDeleteTable() {
        DeleteTableRequest request = new DeleteTableRequest();
        request.setTableName("test1017");
        BaseResponse response = databaseService.deleteTable(request);
        log.info("RESPONSE: {}", response);
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void testQueryTable() {
        QueryTableRequest request = new QueryTableRequest();
        request.setTableName("test1016");
        QueryTableResponse response = databaseService.queryTable(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getTableName(), request.getTableName());
    }

    @Test
    public void testQueryTableNames() {
        QueryTableNamesResponse response = databaseService.queryTableNames();
        log.info("RESPONSE: {}", response);
        Assert.assertFalse(response.tableNames.isEmpty());
    }
}
