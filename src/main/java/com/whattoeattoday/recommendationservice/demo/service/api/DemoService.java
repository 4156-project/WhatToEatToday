package com.whattoeattoday.recommendationservice.demo.service.api;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.demo.request.PrintDetailRequest;

public interface DemoService {
    /**
     * print the demo string
     * @return
     */
    String print();

    /**
     * turn input info into a sentence
     * @param request
     * @return
     */
    BaseResponse printDetail(PrintDetailRequest request);
}
