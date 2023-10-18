package com.whattoeattoday.recommendationservice.command.service.impl;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.command.request.*;
import com.whattoeattoday.recommendationservice.command.service.api.CommandService;
import com.whattoeattoday.recommendationservice.common.PageInfo;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.database.request.row.DeleteRowRequest;
import com.whattoeattoday.recommendationservice.database.request.row.InsertRowRequest;
import com.whattoeattoday.recommendationservice.database.request.row.QueryRowRequest;
import com.whattoeattoday.recommendationservice.database.request.row.UpdateRowRequest;
import com.whattoeattoday.recommendationservice.database.request.table.QueryTableRequest;
import com.whattoeattoday.recommendationservice.database.response.QueryTableNamesResponse;
import com.whattoeattoday.recommendationservice.database.response.QueryTableResponse;
import com.whattoeattoday.recommendationservice.database.service.DatabaseService;
import com.whattoeattoday.recommendationservice.database.service.TableService;
import org.springframework.stereotype.Service;


import com.whattoeattoday.recommendationservice.command.service.impl.SQLTypeValidator;


import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ArrayList;

/**
 * @author Yufeng Wan yw3921@columbia.edu
 * @date 10/16/23
 */
@Service
public class CommandServiceImpl implements CommandService{

    @Resource
    private DatabaseService databaseService;
    @Resource
    private TableService tableService;
    private boolean isTableValid(String tableName) {
        QueryTableNamesResponse response = databaseService.queryTableNames();
        return response.tableNames.contains(tableName);
    }
    @Override
    public BaseResponse Insert(InsertRequest request) {
        //check if the database null
        QueryTableNamesResponse response = databaseService.queryTableNames();
        if (response.tableNames == null || response.tableNames.isEmpty()) {
            return BaseResponse.with(Status.SUCCESS, "Database is Empty");
        }

        //check if table name in database
        String tableName = request.getTableName();
        if(!isTableValid(tableName)){
            return BaseResponse.with(Status.NOT_FOUND, "Table not found");
        }

        //check if the inserted fieldName and value valid
        Map<String,Object>fieldNameValues = request.getFieldNameValues();
        QueryTableRequest request1 = new QueryTableRequest();
        request1.setTableName(tableName);
        QueryTableResponse response1 = databaseService.queryTable(request1);
        Map<String, String> fieldNameTypeMap = response1.getFiledNameTypeMap();

        List<String> fieldNames = new ArrayList<>();
        List<String> values = new ArrayList<>();

        for (Map.Entry<String,Object>entry:fieldNameValues.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();

            if (!fieldNameTypeMap.containsKey(key)){
                return BaseResponse.with(Status.NOT_FOUND, "Column not found");

            }
            String sqlType = fieldNameTypeMap.get(key);
            if (!SQLTypeValidator.isValid(value, sqlType)) {
                return BaseResponse.with(Status.PARAM_ERROR, "Type Error");
            }
            fieldNames.add(entry.getKey());
            values.add(entry.getValue().toString());

        }
        InsertRowRequest request2 = new InsertRowRequest();
        request2.setTableName(tableName);
        request2.setFiledNames(fieldNames);
        request2.setValues(values);
        return tableService.insert(request2);
    }

    @Override
    public BaseResponse Delete(DeleteRequest request) {
        //check if the database null
        QueryTableNamesResponse response = databaseService.queryTableNames();
        if (response.tableNames == null || response.tableNames.isEmpty()) {
            return BaseResponse.with(Status.SUCCESS, "Database is Empty");
        }

        //check if table name in database
        String tableName = request.getTableName();
        if(!isTableValid(tableName)){
            return BaseResponse.with(Status.NOT_FOUND, "Table not found");
        }
        //check if the deleted fieldName null or empty
        String conditionField = request.getConditionField();
        Object conditionValue = request.getConditionValue();

        if(conditionField.isEmpty()){
            return BaseResponse.with(Status.PARAM_ERROR,"FieldName should not be empty");
        }

        DeleteRowRequest request2 = new DeleteRowRequest();
        request2.setTableName(tableName);
        request2.setConditionField(conditionField);
        request2.setConditionValue(conditionValue.toString());

        return tableService.delete(request2);
    }

    @Override
    public BaseResponse Update(UpdateRequest request) {
        //check if the database null
        QueryTableNamesResponse response = databaseService.queryTableNames();
        if (response.tableNames == null || response.tableNames.isEmpty()) {
            return BaseResponse.with(Status.SUCCESS, "Database is Empty");
        }

        //check if table name in database
        String tableName = request.getTableName();
        if(!isTableValid(tableName)){
            return BaseResponse.with(Status.NOT_FOUND, "Table not found");
        }
        //check if the deleted fieldName null or empty
        String conditionField = request.getConditionField();
        Object conditionValue = request.getConditionValue();

        if(conditionField.isEmpty()){
            return BaseResponse.with(Status.PARAM_ERROR,"FieldName should not be empty");
        }

        //check if the update fieldName and value valid
        Map<String,Object>fieldNameValues = request.getFieldNameValues();
        QueryTableRequest request1 = new QueryTableRequest();
        request1.setTableName(tableName);
        QueryTableResponse response1 = databaseService.queryTable(request1);
        Map<String, String> fieldNameTypeMap = response1.getFiledNameTypeMap();

        List<String> fieldNames = new ArrayList<>();
        List<String> values = new ArrayList<>();

        for (Map.Entry<String,Object>entry:fieldNameValues.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();

            if (!fieldNameTypeMap.containsKey(key)){
                return BaseResponse.with(Status.NOT_FOUND, "Column not found");

            }
            String sqlType = fieldNameTypeMap.get(key);
            if (!SQLTypeValidator.isValid(value, sqlType)) {
                return BaseResponse.with(Status.PARAM_ERROR, "Type Error");
            }
            fieldNames.add(entry.getKey());
            values.add(entry.getValue().toString());

        }

        UpdateRowRequest request2 = new UpdateRowRequest();
        request2.setConditionField(conditionField);
        request2.setConditionValue(conditionValue.toString());
        request2.setTableName(tableName);
        request2.setFiledNames(fieldNames);
        request2.setValues(values);

        return tableService.update(request2);
    }
}
