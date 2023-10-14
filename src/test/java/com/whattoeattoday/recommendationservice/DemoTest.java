package com.whattoeattoday.recommendationservice;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.demo.request.PrintDetailRequest;
import com.whattoeattoday.recommendationservice.demo.service.api.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecommendationServiceApplication.class)
public class DemoTest {

    @Autowired
    private DemoService demoService;

    @Test
    public void testPrint() {
        PrintDetailRequest request = new PrintDetailRequest();
        request.setName("TestMan");
        request.setAge("18");
        request.setGender("M");
        BaseResponse response = demoService.printDetail(request);
        // method 1
        Assert.assertEquals(response.getCode(), Status.SUCCESS);
        // method 2
        Assert.assertTrue(response.isSuccess());
        log.info("Response: {}", response);
    }

}
