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
    // 自动装配数据源
    @Autowired
    private DataSource dataSource;

    @Autowired
    private DatabaseService databaseService;

    @Test
    public void contextLoads() throws SQLException {
        // 查看默认数据源
        System.out.println(dataSource.getClass());
        // 获得连接
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        // 关闭连接
        connection.close();
    }

    @Test
    public void testBuildTable() {
        BuildTableRequest request = new BuildTableRequest();

        List<String> fieldNameList = new ArrayList<>();
        fieldNameList.add("id");
        fieldNameList.add("name");
        fieldNameList.add("gender");
        fieldNameList.add("age");
        List<String> fieldTypeList = new ArrayList<>();
        fieldTypeList.add("BIGINT(20)");
        fieldTypeList.add("VARCHAR(20)");
        fieldTypeList.add("VARCHAR(20)");
        fieldTypeList.add("INT");

        request.setTableName("test1016");
        request.setFieldNameList(fieldNameList);
        request.setFieldTypeList(fieldTypeList);
        request.setPrimaryKey("id");

        BaseResponse response = databaseService.buildTable(request);
        log.info("RESPONSE: {}", response);
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void TestSetAutoIncrement() {
        UpdateTableRequest request = new UpdateTableRequest();
        request.setTableName("test1016");
        request.setColumnName("id");
        BaseResponse response = databaseService.setAutoIncrement(request);
        log.info("RESPONSE: {}", response);
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void TestSetUniqueKey() {
        UpdateTableRequest request = new UpdateTableRequest();
        request.setTableName("test1016");
        request.setColumnName("name");
        BaseResponse response = databaseService.setUniqueKey(request);
        log.info("RESPONSE: {}", response);
        Assert.assertTrue(response.isSuccess());
    }
    @Test
    public void TestDeleteTable() {
        DeleteTableRequest request = new DeleteTableRequest();
        request.setTableName("test1016");
        BaseResponse response = databaseService.deleteTable(request);
        log.info("RESPONSE: {}", response);
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void TestQueryTable() {
        QueryTableRequest request = new QueryTableRequest();
        request.setTableName("test1016");
        QueryTableResponse response = databaseService.queryTable(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getTableName(), request.getTableName());
    }

    @Test
    public void TestQueryTableNames() {
        QueryTableNamesResponse response = databaseService.queryTableNames();
        log.info("RESPONSE: {}", response);
        Assert.assertFalse(response.tableNames.isEmpty());
    }
}
