package com.whattoeattoday.recommendationservice.user.service.api;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.user.request.UserLoginRequest;
import com.whattoeattoday.recommendationservice.user.request.UserRegisterRequest;

/**
 * @author Jiarong Shi js6132@columbia.edu
 * @Date 11/24/2023
 */
public interface UserService {
    BaseResponse userRegister(UserRegisterRequest request);

    BaseResponse userLogin(UserLoginRequest request);
}
