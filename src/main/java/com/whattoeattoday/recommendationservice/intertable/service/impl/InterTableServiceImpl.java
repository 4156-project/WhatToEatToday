package com.whattoeattoday.recommendationservice.intertable.service.impl;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.ParamUtil;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.database.request.table.BuildTableRequest;
import com.whattoeattoday.recommendationservice.database.request.table.DeleteTableRequest;
import com.whattoeattoday.recommendationservice.database.service.DatabaseService;
import com.whattoeattoday.recommendationservice.intertable.service.api.InterTableService;
import com.whattoeattoday.recommendationservice.query.service.api.QueryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ruoxuanwang rw2961@columbia.edu
 * @date 10/17/2023
 */
@Service
public class InterTableServiceImpl implements InterTableService {
    @Resource
    private DatabaseService databaseService;

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
        //check if the name list is empty
        if (fieldNameList==null || fieldNameList.isEmpty()) {
            return BaseResponse.with(Status.PARAM_ERROR, "No Field");
        }
        if (!ParamUtil.isAllNotBlank(fieldNameList.toArray(new String[0]))) {
            return BaseResponse.with(Status.PARAM_ERROR, "FieldNameList is Incomplete");
        }
        //check if the type list is empty
        if (fieldTypeList==null || fieldNameList.isEmpty()) {
            return BaseResponse.with(Status.PARAM_ERROR, "No Type");
        }
        if (!ParamUtil.isAllNotBlank(fieldTypeList.toArray(new String[0]))) {
            return BaseResponse.with(Status.PARAM_ERROR, "FieldTypeList is Incomplete");
        }
        //check if table name already existed
        if (ParamUtil.isTableName(tableName)) {
            return BaseResponse.with(Status.PARAM_ERROR, String.format("Table %s Already Created", tableName));
        }
        //check if the lengths of field names and types are equal
        if (fieldTypeList.size() != fieldNameList.size()){
            return BaseResponse.with(Status.PARAM_ERROR, "Unequal Length of Field Name and Field Type");
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
        BaseResponse response = databaseService.buildTable(request);
        if (response.getCode() == Status.SUCCESS) {
            response.setMessage("Table " + tableName + " is created");
        }
        return response;
    }

    @Override
    public BaseResponse deleteTable(DeleteTableRequest request) {
        String tableName = request.getTableName();
        //check if there is any blank string
        if (ParamUtil.isBlank(tableName)) {
            return BaseResponse.with(Status.PARAM_ERROR, "Param is Incomplete");
        }
        //check if table exists
        if (!ParamUtil.isTableName(tableName)) {
            return BaseResponse.with(Status.PARAM_ERROR, "Table " + tableName + " Does Not Exist");
        }
        BaseResponse response = databaseService.deleteTable(request);
        if (response.getCode() == Status.SUCCESS) {
            response.setMessage("Table " + tableName + " is deleted");
        }
        return response;
    }

}
