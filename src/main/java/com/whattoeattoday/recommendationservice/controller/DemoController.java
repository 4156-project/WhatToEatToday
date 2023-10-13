package com.whattoeattoday.recommendationservice.controller;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.request.PrintDetailRequest;
import com.whattoeattoday.recommendationservice.response.PrintDetailResponse;
import com.whattoeattoday.recommendationservice.service.api.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DemoController {

    @Autowired
    private DemoService demoService;

    @RequestMapping("/demo")
    public String demo() {
        return demoService.print();
    }

    @PostMapping("/demo/print-detail")
    public BaseResponse printDetail(@RequestBody PrintDetailRequest request) {
        return demoService.printDetail(request);
    }
}
