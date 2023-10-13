package com.whattoeattoday.recommendationservice.service.api;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.request.PrintDetailRequest;
import com.whattoeattoday.recommendationservice.response.PrintDetailResponse;

public interface DemoService {
    String print();

    BaseResponse printDetail(PrintDetailRequest request);
}
