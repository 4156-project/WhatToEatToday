package com.whattoeattoday.recommendationservice.demo.service.impl;

import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.demo.request.PrintDetailRequest;
import com.whattoeattoday.recommendationservice.demo.service.api.DemoService;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public String print() {
        return "Hello";
    }

    @Override
    public BaseResponse printDetail(PrintDetailRequest request) {

        if (request.age == null || request.name == null || request.gender == null) {
            return BaseResponse.with(Status.PARAM_ERROR);
        }
        String str = String.format("%s is a %s years old %s.",
                request.name,
                request.age,
                "M".equals(request.gender) ? "man" : "woman");
        return BaseResponse.with(Status.SUCCESS, "OK", str);
    }
}
