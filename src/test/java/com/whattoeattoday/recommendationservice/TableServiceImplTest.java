package com.whattoeattoday.recommendationservice;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.PageInfo;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.database.request.row.*;
import com.whattoeattoday.recommendationservice.database.service.impl.TableServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecommendationServiceApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TableServiceImplTest {
    @Autowired
    private TableServiceImpl tableService;

    @Test
    public void test0Insert() {
        InsertRowRequest request = new InsertRowRequest();
        List<String> fieldNames = new ArrayList<String>(){{
            add("title");
            add("description");
        }};
        List<String> values = new ArrayList<String>(){{
            add("first head java");
            add("entry-level guide");
        }};
        request.setTableName("test1018");
        request.setFiledNames(fieldNames);
        request.setValues(values);
        BaseResponse response = tableService.insert(request);
        log.info("RESPONSE: {}", response);
        Assert.assertTrue(response.isSuccess());
        //Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        fieldNames = new ArrayList<String>(){{
            add("name");
            add("gender");
            add("age");
        }};
        values = new ArrayList<String>(){{
            add("Tom");
            add("male");
            add("24");
        }};
        request.setTableName("test1016");
        request.setFiledNames(fieldNames);
        request.setValues(values);
        response = tableService.insert(request);
        log.info("RESPONSE: {}", response);
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void test5Delete() {
        DeleteRowRequest request = new DeleteRowRequest();
        request.setTableName("test1018");
        request.setConditionField("title");
        request.setConditionValue("first head java");
        BaseResponse response = tableService.delete(request);
        log.info("RESPONSE: {}", response);
        //Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void testDeletePlus() {
        DeleteRowPlusRequest deleteRowPlusRequest = new DeleteRowPlusRequest();
        deleteRowPlusRequest.setTableName("test1016");
        List<String> conditions = new ArrayList<>();
        conditions.add("'male'");
        conditions.add("24");
        List<String> fields = new ArrayList<>();
        fields.add("gender");
        fields.add("age");
        deleteRowPlusRequest.setConditionFields(fields);
        deleteRowPlusRequest.setConditionValues(conditions);
        BaseResponse response = tableService.delete(deleteRowPlusRequest);
        log.info("RESPONSE: {}", response);
        //Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void test1Update() {
        UpdateRowRequest request = new UpdateRowRequest();
        List<String> fieldNames = new ArrayList<String>(){{
            add("name");
            add("gender");
            add("age");
        }};
        List<String> values = new ArrayList<String>(){{
            add("Tom");
            add("male");
            add("24");
        }};
        request.setTableName("test1016");
        request.setFiledNames(fieldNames);
        request.setValues(values);
        request.setConditionField("name");
        request.setConditionValue("Larry");
        BaseResponse response = tableService.update(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        //Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void test2Query() {
        QueryRowRequest request = QueryRowRequest.builder().build();
        request.setTableName("test1016");
        List<String> fieldNames = new ArrayList<String>(){{
            add("name");
            add("gender");
        }};
        request.setFieldNames(fieldNames);
        request.setPageInfo(PageInfo.builder().pageNo(2).pageSize(1).build());
        PageInfo pageInfo = tableService.query(request);
        log.info("RESPONSE1: {}", pageInfo);
        Assert.assertTrue(pageInfo.getPageData().size() == 1);

        request.setPageInfo(PageInfo.builder().pageNo(1).pageSize(2).build());
        PageInfo pageInfo2 = tableService.query(request);
        log.info("RESPONSE2: {}", pageInfo2);
        Assert.assertTrue(pageInfo2.getPageData().size() == 2);
    }

    @Test
    public void test3QueryTableRowsNum() {
        long res = tableService.queryTableRowsNum("test1016");
        Assert.assertEquals(res, 5);
    }

    @Test
    public void test4UpdateRowNum() {
        BaseResponse response = tableService.updateRowNum("test1016");
        Assert.assertTrue(response.isSuccess());
    }
}