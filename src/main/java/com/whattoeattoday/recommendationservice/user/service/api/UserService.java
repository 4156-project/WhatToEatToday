package com.whattoeattoday.recommendationservice.user.service.api;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.user.model.User;
import com.whattoeattoday.recommendationservice.user.request.UserCollectionRequest;
import com.whattoeattoday.recommendationservice.user.request.UserLoginRequest;
import com.whattoeattoday.recommendationservice.user.request.UserRegisterRequest;
import com.whattoeattoday.recommendationservice.user.request.UserVerifyRequest;

/**
 * @author Jiarong Shi js6132@columbia.edu
 * @Date 11/24/2023
 */
public interface UserService {
    /**
     * user register
     * @param request
     * @return
     */
    BaseResponse userRegister(UserRegisterRequest request);

    /**
     * user login
     * @param request
     * @return
     */
    BaseResponse userLogin(UserLoginRequest request);

    /**
     * verify if user information correct
     * @param request
     * @return
     */
    BaseResponse userVerify(UserVerifyRequest request);

    /**
     * add item into user's collection
     * @param request
     * @return
     */
    BaseResponse userAddCollection(UserCollectionRequest request);

    /**
     * delete item from user's collection
     * @param request
     * @return
     */
    BaseResponse userDeleteCollection(UserCollectionRequest request);

    /**
     * get user by username
     * @param username
     * @return
     */
    User getUserByUsername(String username);
}
