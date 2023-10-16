package com.whattoeattoday.recommendationservice.database.service.impl;

import com.whattoeattoday.recommendationservice.RecommendationServiceApplication;
import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.PageInfo;
import com.whattoeattoday.recommendationservice.database.request.row.DeleteRowRequest;
import com.whattoeattoday.recommendationservice.database.request.row.InsertRowRequest;
import com.whattoeattoday.recommendationservice.database.request.row.QueryRowRequest;
import com.whattoeattoday.recommendationservice.database.request.row.UpdateRowRequest;
import com.whattoeattoday.recommendationservice.database.service.TableService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecommendationServiceApplication.class)
public class TableServiceImplTest {
    @Autowired
    private TableService tableService;

    @Test
    public void testInsert() {
        InsertRowRequest request = new InsertRowRequest();
        List<String> fieldNames = new ArrayList<String>(){{
            add("name");
            add("gender");
            add("age");
        }};
        List<String> values = new ArrayList<String>(){{
            add("Nick");
            add("male");
            add("23");
        }};
        request.setTableName("test1016");
        request.setFiledNames(fieldNames);
        request.setValues(values);
        BaseResponse response = tableService.insert(request);
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void testDelete() {
        DeleteRowRequest request = new DeleteRowRequest();
        request.setTableName("test1016");
        request.setConditionField("name");
        request.setConditionValue("Nick");
        BaseResponse response = tableService.delete(request);
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void testUpdate() {
        UpdateRowRequest request = new UpdateRowRequest();
        List<String> fieldNames = new ArrayList<String>(){{
            add("name");
            add("gender");
            add("age");
        }};
        List<String> values = new ArrayList<String>(){{
            add("Larry");
            add("male");
            add("24");
        }};
        request.setTableName("test1016");
        request.setFiledNames(fieldNames);
        request.setValues(values);
        request.setConditionField("name");
        request.setConditionValue("Larry");
        BaseResponse response = tableService.update(request);
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void testQuery() {
        QueryRowRequest request = new QueryRowRequest();
        request.setTableName("test1016");
        List<String> fieldNames = new ArrayList<String>(){{
            add("name");
            add("gender");
        }};
        request.setFieldNames(fieldNames);
        request.setPageNo("1");
        request.setPageSize("10");
//        request.setConditionField("name");
//        request.setConditionValue("Larry");
        PageInfo pageInfo = tableService.query(request);
        System.out.println(pageInfo);
    }
}