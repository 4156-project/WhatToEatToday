package com.whattoeattoday.recommendationservice.user.controller;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.user.request.UserCollectionRequest;
import com.whattoeattoday.recommendationservice.user.request.UserLoginRequest;
import com.whattoeattoday.recommendationservice.user.request.UserRegisterRequest;
import com.whattoeattoday.recommendationservice.user.request.UserVerifyRequest;
import com.whattoeattoday.recommendationservice.user.service.api.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Jiarong Shi js6132@columbia.edu
 * @Date 11/24/2023
 */
@RestController
public class UserController {

    @Resource
    public UserService userService;

    /**
     * user register
     * @param request
     */
    @PostMapping("/user/register")
    public BaseResponse userRegister(@RequestBody UserRegisterRequest request) {
        return userService.userRegister(request);
    }

    /**
     * user login
     * @param request
     */
    @PostMapping("/user/login")
    public BaseResponse userLogin(@RequestBody UserLoginRequest request) {
        return userService.userLogin(request);
    }

    /**
     * check whether the user has the authority of certain category
     * @param request
     */
    @PostMapping("/user/verify")
    public BaseResponse userVerify(@RequestBody UserVerifyRequest request) {
        return userService.userVerify(request);
    }

    /**
     * add collection to user
     * @param request
     */
    @PostMapping("/user/add/collection")
    public BaseResponse userAddCollection(@RequestBody UserCollectionRequest request) {
        return userService.userAddCollection(request);
    }

    /**
     * delete collection to user
     * @param request
     */
    @PostMapping("/user/delete/collection")
    public BaseResponse userDeleteCollection(@RequestBody UserCollectionRequest request) {
        return userService.userDeleteCollection(request);
    }
}
