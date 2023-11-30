package com.whattoeattoday.recommendationservice.user.service.impl;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.PageInfo;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.database.request.row.DeleteRowPlusRequest;
import com.whattoeattoday.recommendationservice.database.request.row.QueryRowRequest;
import com.whattoeattoday.recommendationservice.database.service.TableService;
import com.whattoeattoday.recommendationservice.intratable.request.InsertRequest;
import com.whattoeattoday.recommendationservice.intratable.service.api.IntraTableService;
import com.whattoeattoday.recommendationservice.query.service.api.QueryService;
import com.whattoeattoday.recommendationservice.user.model.User;
import com.whattoeattoday.recommendationservice.user.request.UserCollectionRequest;
import com.whattoeattoday.recommendationservice.user.request.UserLoginRequest;
import com.whattoeattoday.recommendationservice.user.request.UserRegisterRequest;
import com.whattoeattoday.recommendationservice.user.request.UserVerifyRequest;
import com.whattoeattoday.recommendationservice.user.service.api.UserService;
import com.whattoeattoday.recommendationservice.user.utils.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jiarong Shi js6132@columbia.edu
 * @Date 11/24/2023
 */

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private TableService tableService;
    @Resource
    private IntraTableService intraTableService;
    @Resource
    private QueryService queryService;

    private final static String salt = "1a2b3c";

    @Override
    public BaseResponse userRegister(UserRegisterRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();
        String category = request.getCategory();
        // valid check
        if (username == null || username.length() == 0) {
            return BaseResponse.with(Status.NOT_FOUND, "Username Not Found");
        }
        if (password == null || password.length() == 0) {
            return BaseResponse.with(Status.NOT_FOUND, "Password Not Found");
        }
        if (email == null || email.length() == 0) {
            return BaseResponse.with(Status.NOT_FOUND, "Email Not Found");
        } else if (!checkEmail(email)) {
            return BaseResponse.with(Status.PARAM_ERROR, "Email Not Valid");
        }
        BaseResponse queryResponse = queryService.queryAllCategoryName();
        Set<String> categories = (Set<String>) queryResponse.getData();
        if (category == null || category.length() == 0 || !categories.contains(category)) {
            return BaseResponse.with(Status.NOT_FOUND, "Category Not Found");
        }

        String encodedPassword = MD5Util.formPassToDBPass(password, salt);
        // check if user has already existed
        User user = getUserByUsername(username);
        if (user != null) {
            return BaseResponse.with(Status.DUPLICATE_ERROR, "User already exists!");
        }
        // insert into db table
        InsertRequest insertRequest = new InsertRequest();
        insertRequest.setTableName("user");
        Map<String, Object> fieldNameValues = new HashMap<>();
        fieldNameValues.put("username", username);
        fieldNameValues.put("password", encodedPassword);
        fieldNameValues.put("email", email);
        fieldNameValues.put("category", category);
        insertRequest.setFieldNameValues(fieldNameValues);
        BaseResponse response = intraTableService.insert(insertRequest);

        return response;
    }

    @Override
    public BaseResponse userLogin(UserLoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        if (username == null || username.length() == 0) {
            return BaseResponse.with(Status.NOT_FOUND, "Username Not Found");
        }
        if (password == null || password.length() == 0) {
            return BaseResponse.with(Status.NOT_FOUND, "Password Not Found");
        }
        User user = getUserByUsername(username);
        if (user == null) {
            return BaseResponse.with(Status.NOT_FOUND, "User Not Found!");
        }
        String encodedPassword = MD5Util.formPassToDBPass(password, salt);
        if (!encodedPassword.equals(user.getEncodedPassword())) {
            return BaseResponse.with(Status.PARAM_ERROR, "Password Not Valid");
        }

        return BaseResponse.with(Status.SUCCESS,"Login Success!", user);
    }

    @Override
    public BaseResponse userVerify(UserVerifyRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        String category = request.getCategory();
        if (username == null || username.length() == 0) {
            return BaseResponse.with(Status.NOT_FOUND, "Username Not Found");
        }
        if (password == null || password.length() == 0) {
            return BaseResponse.with(Status.NOT_FOUND, "Password Not Found");
        }
        BaseResponse queryResponse = queryService.queryAllCategoryName();
        Set<String> categories = (Set<String>) queryResponse.getData();
        if (category == null || category.length() == 0 || !categories.contains(category)) {
            return BaseResponse.with(Status.NOT_FOUND, "Category Not Found");
        }
        // check user
        if (!checkUser(username, password, category)) {
            return BaseResponse.with(Status.PARAM_ERROR, "User Not Valid");
        }
        return BaseResponse.with(Status.SUCCESS, "Verify Success!");
    }

    @Override
    public BaseResponse userAddCollection(UserCollectionRequest request) {
        UserVerifyRequest userVerifyRequest = new UserVerifyRequest();
        userVerifyRequest.setUsername(request.getUsername());
        userVerifyRequest.setPassword(request.getPassword());
        userVerifyRequest.setCategory(request.getCategory());
        BaseResponse response = userVerify(userVerifyRequest);
        if (response.getCode() != Status.SUCCESS) {
            return BaseResponse.with(Status.PARAM_ERROR, "User Not Valid");
        }
        Long itemId = Long.parseLong(request.getItemId());
        if (!checkItemInCategory(itemId, request.getCategory())) {
            return BaseResponse.with(Status.PARAM_ERROR, "Category or Item Not Valid");
        }
        User user = getUserByUsername(request.getUsername());
        Set<Long> collection = user.getCollection();
        if (collection.contains(itemId)) {
            return BaseResponse.with(Status.PARAM_ERROR, "Item has Existed");
        } else {
            collection.add(itemId);
        }
        // write into collection table
        InsertRequest insertRequest = new InsertRequest();
        insertRequest.setTableName("collection");
        Map<String, Object> fieldNameValues = new HashMap<>();
        fieldNameValues.put("user_id", Integer.parseInt(user.getId()));
        fieldNameValues.put("category_name", user.getCategory());
        fieldNameValues.put("item_id", Integer.parseInt(request.getItemId()));
        insertRequest.setFieldNameValues(fieldNameValues);
        BaseResponse insertResponse = intraTableService.insert(insertRequest);
        if (insertResponse.getCode() == Status.SUCCESS) {
            return BaseResponse.with(Status.SUCCESS,"Add Collection Success!", user);
        } else {
            return BaseResponse.with(Status.DATABASE_ERROR,"Add Collection Fail!");
        }
    }

    @Override
    public BaseResponse userDeleteCollection(UserCollectionRequest request) {
        UserVerifyRequest userVerifyRequest = new UserVerifyRequest();
        userVerifyRequest.setUsername(request.getUsername());
        userVerifyRequest.setPassword(request.getPassword());
        userVerifyRequest.setCategory(request.getCategory());
        BaseResponse response = userVerify(userVerifyRequest);
        if (response.getCode() != Status.SUCCESS) {
            return BaseResponse.with(Status.PARAM_ERROR, "User Not Valid");
        }
        Long itemId = Long.parseLong(request.getItemId());
        if (!checkItemInCategory(itemId, request.getCategory())) {
            return BaseResponse.with(Status.PARAM_ERROR, "Category or Item Not Valid");
        }
        User user = getUserByUsername(request.getUsername());
        Set<Long> collection = user.getCollection();
        if (!collection.contains(itemId)) {
            return BaseResponse.with(Status.PARAM_ERROR, "Item not Existed");
        } else {
            collection.remove(itemId);
        }
        // write into collection table
        DeleteRowPlusRequest deleteRequest = new DeleteRowPlusRequest();
        deleteRequest.setTableName("collection");
        List<String> conditions = new ArrayList<>();
        conditions.add(user.getId());
        conditions.add(request.getItemId());
        List<String> fields = new ArrayList<>();
        fields.add("user_id");
        fields.add("item_id");
        deleteRequest.setConditionFields(fields);
        deleteRequest.setConditionValues(conditions);
        BaseResponse deleteResponse = tableService.delete(deleteRequest);
        if (deleteResponse.getCode() == Status.SUCCESS) {
            return BaseResponse.with(Status.SUCCESS,"Delete Collection Success!", user);
        } else {
            return BaseResponse.with(Status.DATABASE_ERROR,"Delete Collection Fail!");
        }
    }

    private boolean checkItemInCategory(Long itemId, String category) {
        // check whether category valid
        BaseResponse queryResponse = queryService.queryAllCategoryName();
        Set<String> categories = (Set<String>) queryResponse.getData();
        if (category == null || category.length() == 0 || !categories.contains(category)) {
            return false;
        }
        // check whether itemId included in category
        QueryRowRequest queryRowRequest = QueryRowRequest.builder().build();
        queryRowRequest.setTableName(category);
        List<String> fieldNames = new ArrayList<>();
        fieldNames.add("id");
        queryRowRequest.setFieldNames(fieldNames);
        queryRowRequest.setConditionField("id");
        queryRowRequest.setConditionValue(String.valueOf(itemId));
        queryRowRequest.setPageInfo(PageInfo.builder().pageNo(1).pageSize(1).build());
        PageInfo response = tableService.query(queryRowRequest);
        List<Map<String,Object>> dataList = response.getPageData();
        if (dataList.size() == 0) {
            return false;
        }
        return true;
    }

    private boolean checkUser(String username, String password, String category) {
        User user = getUserByUsername(username);
        String encodedPassword = MD5Util.formPassToDBPass(password, salt);
        if (user == null || !user.getEncodedPassword().equals(encodedPassword) || !user.getCategory().equals(category)) {
            return false;
        }
        return true;
    }

    private boolean checkEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private User getUserByUsername(String username) {
        QueryRowRequest queryRowRequest = QueryRowRequest.builder().build();
        queryRowRequest.setTableName("user");
        List<String> fieldNames = new ArrayList<>();
        fieldNames.add("id");
        fieldNames.add("username");
        fieldNames.add("password");
        fieldNames.add("email");
        fieldNames.add("category");
        queryRowRequest.setFieldNames(fieldNames);
        queryRowRequest.setConditionField("username");
        queryRowRequest.setConditionValue(username);
        queryRowRequest.setPageInfo(PageInfo.builder().pageNo(1).pageSize(1).build());
        PageInfo response = tableService.query(queryRowRequest);
        List<Map<String,Object>> dataList = response.getPageData();
        if (dataList.size() == 0) {
            return null;
        } else {
            Map<String,Object> data = dataList.get(0);
            User user = new User();
            user.setId(String.valueOf(data.get("id")));
            user.setUsername(String.valueOf(data.get("username")));
            user.setEncodedPassword(String.valueOf(data.get("password")));
            user.setEmail(String.valueOf(data.get("email")));
            String category = String.valueOf(data.get("category"));
            user.setCategory(category);
            user.setCollection(getUserCollection(String.valueOf(data.get("id"))));
            return user;
        }
    }

    private Set<Long> getUserCollection(String id) {
        QueryRowRequest queryRowRequest = QueryRowRequest.builder().build();
        queryRowRequest.setTableName("collection");
        List<String> fieldNames = new ArrayList<>();
        fieldNames.add("user_id");
        fieldNames.add("category_name");
        fieldNames.add("item_id");
        queryRowRequest.setFieldNames(fieldNames);
        queryRowRequest.setConditionField("user_id");
        queryRowRequest.setConditionValue(id);
        queryRowRequest.setPageInfo(PageInfo.builder().pageNo(1).pageSize(10).build());
        PageInfo response = tableService.query(queryRowRequest);
        List<Map<String,Object>> queryData = response.getPageData();
        Set<Long> collection = new HashSet<>();
        for (Map<String, Object> oneRow : queryData) {
            collection.add((Long)oneRow.get("item_id"));
        }
        return collection;
    }
}
