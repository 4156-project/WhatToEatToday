package com.whattoeattoday.recommendationservice.intertable.service.impl;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.ParamUtil;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.database.request.table.BuildTableRequest;
import com.whattoeattoday.recommendationservice.database.request.table.DeleteTableRequest;
import com.whattoeattoday.recommendationservice.database.request.table.UpdateTableRequest;
import com.whattoeattoday.recommendationservice.database.response.QueryTableResponse;
import com.whattoeattoday.recommendationservice.database.service.DatabaseService;
import com.whattoeattoday.recommendationservice.intertable.service.api.InterTableService;
import com.whattoeattoday.recommendationservice.query.request.QueryCategoryInfoRequest;
import com.whattoeattoday.recommendationservice.query.service.api.QueryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ruoxuanwang rw2961@columbia.edu
 * @date 10/17/2023
 */
@Service
public class InterTableServiceImpl implements InterTableService {
    @Resource
    private DatabaseService databaseService;

    @Resource
    private QueryService queryService;

    @Override
    public BaseResponse createTable(BuildTableRequest request) {
        String tableName = request.getTableName();
        List<String> fieldNameList = request.getFieldNameList();
        List<String> fieldTypeList = request.getFieldTypeList();
        String primaryKey = request.getPrimaryKey();
        String uniqueKey = request.getUniqueKey();
        String autoIncrementField = request.getAutoIncrementField();
        //check if there is any blank string
        if (ParamUtil.isBlank(tableName) || ParamUtil.isBlank(primaryKey)) {
            return BaseResponse.with(Status.PARAM_ERROR, "Param is Incomplete");
        }
        for (String s : fieldNameList) {
            if (ParamUtil.isBlank(s)) {
                return BaseResponse.with(Status.PARAM_ERROR, "FieldNameList is Incomplete");
            }
        }
        //check if table name already existed
        if (databaseService.queryTableNames( ).tableNames.contains(tableName)) {
            return BaseResponse.with(Status.PARAM_ERROR, "Table " + tableName + " Already Created");
        }
        //check if the lengths of field names and types are equal
        if (fieldTypeList.size() != fieldNameList.size()){
            return BaseResponse.with(Status.PARAM_ERROR, "Unequal Length of Field Name and Field Type");
        }
        //check if the name list is empty
        if (fieldNameList.isEmpty()) {
            return BaseResponse.with(Status.PARAM_ERROR, "No Field");
        }
        //check if the primary key is in the name list
        if (!fieldNameList.contains(primaryKey)) {
            return BaseResponse.with(Status.PARAM_ERROR, "Invalid Primary Key " + primaryKey);
        }
        //check if the field types are valid
        for (String t : fieldTypeList) {
            if (!ParamUtil.isTypeValid(t)) {
                return BaseResponse.with(Status.PARAM_ERROR, "Invalid Field Type: "+t);
            }
        }
        //check if uniqueKey field is valid
        if (uniqueKey != null) {
            if (!fieldNameList.contains(uniqueKey)) {
                return BaseResponse.with(Status.PARAM_ERROR, "Invalid Unique Key " + uniqueKey);
            }
        }
        // check if autoincrement field is valid
        if (autoIncrementField != null) {
            if (!fieldNameList.contains(autoIncrementField)) {
                return BaseResponse.with(Status.PARAM_ERROR, "Invalid Auto Increment Field " + autoIncrementField);
            }
        }
        return databaseService.buildTable(request);
    }

    @Override
    public BaseResponse deleteTable(DeleteTableRequest request) {
        String tableName = request.getTableName();
        //check if there is any blank string
        if (ParamUtil.isBlank(tableName)) {
            return BaseResponse.with(Status.PARAM_ERROR, "Param is Incomplete");
        }
        //check if table exists
        if (!databaseService.queryTableNames( ).tableNames.contains(tableName)) {
            return BaseResponse.with(Status.PARAM_ERROR, "Table " + tableName + " Does Not Exist");
        }
        return databaseService.deleteTable(request);
    }

    @Override
    public BaseResponse setAutoIncrement(UpdateTableRequest request) {
        String tableName = request.getTableName();
        String columnName = request.getColumnName();
        //check if there is any blank string
        if (ParamUtil.isBlank(tableName) || ParamUtil.isBlank(columnName)) {
            return BaseResponse.with(Status.PARAM_ERROR, "Param is Incomplete");
        }
        //check if table exists
        if (!databaseService.queryTableNames( ).tableNames.contains(tableName)) {
            return BaseResponse.with(Status.PARAM_ERROR, "Table Does Not Exist");
        }
        //check if column exists
        QueryCategoryInfoRequest fieldRequest = new QueryCategoryInfoRequest();
        fieldRequest.setCategoryName(tableName);
        QueryTableResponse fieldResponse = queryService.queryCategoryInfo(fieldRequest).getData();
        Map<String, String> filedNameTypeMap = fieldResponse.getFiledNameTypeMap();
        List<String> filedNameList = new ArrayList<>(filedNameTypeMap.keySet());
        if (!filedNameList.contains(columnName)) {
            return BaseResponse.with(Status.PARAM_ERROR, "Field Does Not Exist");
        }
        return databaseService.setAutoIncrement(request);
    }
}
