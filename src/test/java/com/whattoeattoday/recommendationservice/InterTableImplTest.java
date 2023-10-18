package com.whattoeattoday.recommendationservice;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.database.request.table.BuildTableRequest;
import com.whattoeattoday.recommendationservice.intertable.service.api.InterTableService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruoxuan Wang
 * @Date
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecommendationServiceApplication.class)
public class InterTableImplTest {
    @Resource
    private InterTableService interTableService;

    @Test
    public void TestCreateTable() {
        BuildTableRequest request = new BuildTableRequest();

        request.setTableName("book");

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
        log.info("RESPONSE: {}", response);
        Assert.assertTrue(response.isSuccess());
    }
}
