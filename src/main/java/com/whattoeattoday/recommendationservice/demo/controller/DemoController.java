package com.whattoeattoday.recommendationservice.demo.controller;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.demo.request.PrintDetailRequest;
import com.whattoeattoday.recommendationservice.demo.service.api.DemoService;
import io.github.yedaxia.apidocs.Ignore;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Ignore
@RestController
public class DemoController {

    @Resource
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
