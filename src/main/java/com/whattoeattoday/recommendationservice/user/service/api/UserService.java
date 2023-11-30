package com.whattoeattoday.recommendationservice.user.service.api;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.user.request.UserCollectionRequest;
import com.whattoeattoday.recommendationservice.user.request.UserLoginRequest;
import com.whattoeattoday.recommendationservice.user.request.UserRegisterRequest;
import com.whattoeattoday.recommendationservice.user.request.UserVerifyRequest;

/**
 * @author Jiarong Shi js6132@columbia.edu
 * @Date 11/24/2023
 */
public interface UserService {
    BaseResponse userRegister(UserRegisterRequest request);

    BaseResponse userLogin(UserLoginRequest request);

    BaseResponse userVerify(UserVerifyRequest request);

    BaseResponse userAddCollection(UserCollectionRequest request);

    BaseResponse userDeleteCollection(UserCollectionRequest request);
}
