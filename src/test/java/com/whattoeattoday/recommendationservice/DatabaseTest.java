package com.whattoeattoday.recommendationservice;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.database.request.BuildTableRequest;
import com.whattoeattoday.recommendationservice.database.request.QueryTableRequest;
import com.whattoeattoday.recommendationservice.database.service.DatabaseService;
import lombok.extern.slf4j.Slf4j;
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
        fieldTypeList.add("VARCHAR(1)");
        fieldTypeList.add("INT");

        request.setTableName("test1015");
        request.setFieldNameList(fieldNameList);
        request.setFieldTypeList(fieldTypeList);
        request.setPrimaryKey("id");

        BaseResponse response = databaseService.buildTable(request);
        System.out.println(response);
    }

    @Test
    public void TestDeleteTable() {
        QueryTableRequest request = new QueryTableRequest();
        request.setTableName("test1015");
        BaseResponse response = databaseService.deleteTable(request);
        System.out.println(response);
    }

    @Test
    public void TestQueryTable() {
        QueryTableRequest request = new QueryTableRequest();
        request.setTableName("test1015");
        BaseResponse response = databaseService.queryTable(request);
        System.out.println(response);
    }
}
