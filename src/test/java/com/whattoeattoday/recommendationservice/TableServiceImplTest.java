package com.whattoeattoday.recommendationservice;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.PageInfo;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.database.request.row.DeleteRowRequest;
import com.whattoeattoday.recommendationservice.database.request.row.InsertRowRequest;
import com.whattoeattoday.recommendationservice.database.request.row.QueryRowRequest;
import com.whattoeattoday.recommendationservice.database.request.row.UpdateRowRequest;
import com.whattoeattoday.recommendationservice.database.service.impl.TableServiceImpl;
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
    private TableServiceImpl tableService;

    @Test
    public void testInsert() {
        InsertRowRequest request = new InsertRowRequest();
        List<String> fieldNames = new ArrayList<String>(){{
            add("title");
            add("description");
        }};
        List<String> values = new ArrayList<String>(){{
            add("1984");
            add("Another great book!");
        }};
        request.setTableName("test1018");
        request.setFiledNames(fieldNames);
        request.setValues(values);
        BaseResponse response = tableService.insert(request);
        log.info("RESPONSE: {}", response);
        Assert.assertTrue(response.isSuccess());
        //Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
    }

    @Test
    public void testDelete() {
        DeleteRowRequest request = new DeleteRowRequest();
        request.setTableName("test1016");
        request.setConditionField("name");
        request.setConditionValue("Daniel");
        BaseResponse response = tableService.delete(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        //Assert.assertTrue(response.isSuccess());
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
        request.setConditionValue("Tom");
        BaseResponse response = tableService.update(request);
        log.info("RESPONSE: {}", response);
        Assert.assertEquals(response.getCode(), Status.NOT_FOUND);
        //Assert.assertTrue(response.isSuccess());
    }

    @Test
    public void testQuery() {
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
    public void testQueryTableRowsNum() {
        long res = tableService.queryTableRowsNum("test1016");
        Assert.assertEquals(res, 4);
    }

    @Test
    public void testUpdateRowNum() {
        BaseResponse response = tableService.updateRowNum("test1016");
        Assert.assertTrue(response.isSuccess());
    }
}