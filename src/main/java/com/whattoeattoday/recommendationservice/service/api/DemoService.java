package com.whattoeattoday.recommendationservice.service.api;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.request.PrintDetailRequest;

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
