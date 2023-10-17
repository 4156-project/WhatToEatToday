package com.whattoeattoday.recommendationservice.database.service.impl;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.PageInfo;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.database.request.row.DeleteRowRequest;
import com.whattoeattoday.recommendationservice.database.request.row.InsertRowRequest;
import com.whattoeattoday.recommendationservice.database.request.row.QueryRowRequest;
import com.whattoeattoday.recommendationservice.database.request.row.UpdateRowRequest;
import com.whattoeattoday.recommendationservice.database.service.DatabaseService;
import com.whattoeattoday.recommendationservice.database.service.TableService;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TableServiceImpl implements TableService {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private DatabaseService databaseService;

    @Override
    public BaseResponse insert(InsertRowRequest request) {
        String tableName = request.getTableName();
        List<String> fieldNames = request.getFiledNames();
        List<String> values = request.getValues();
        String columnNames = String.join(",", fieldNames);
        String columnValues = String.join("','", values);
        columnValues = "'" + columnValues + "'";
        StringBuilder insertSql = new StringBuilder(String.format("INSERT IGNORE INTO `%s` (%s) VALUES (%s);", tableName,
                columnNames, columnValues));
        try {
            jdbcTemplate.execute(insertSql.toString());
        } catch (DataAccessException e) {
            return BaseResponse.with(Status.PARAM_ERROR);
        }
        // update table_record
        return updateRowNum(tableName);
    }

    @Override
    public BaseResponse delete(DeleteRowRequest request) {
        String tableName = request.getTableName();
        String fieldName = request.getConditionField();
        String condition = request.getConditionValue();
        StringBuilder deleteSql = new StringBuilder(String.format("DELETE FROM `%s` WHERE %s = '%s';", tableName, fieldName, condition));
        try {
            jdbcTemplate.execute(deleteSql.toString());
        } catch (DataAccessException e) {
            return BaseResponse.with(Status.PARAM_ERROR);
        }
        return updateRowNum(tableName);
    }

    @Override
    public BaseResponse update(UpdateRowRequest request) {
        String tableName = request.getTableName();
        String fieldName = request.getConditionField();
        String condition = request.getConditionValue();
        List<String> fieldNames = request.getFiledNames();
        List<String> values = request.getValues();
        StringBuilder updateSql = new StringBuilder(String.format("UPDATE `%s` SET ", tableName));
        for (int i = 0; i < fieldNames.size(); i++) {
            updateSql.append(fieldNames.get(i) + "='" + values.get(i) + "'");
            if (i != fieldNames.size() - 1) {
                updateSql.append(",");
            }
        }
        updateSql.append(String.format(" WHERE %s = '%s';", fieldName, condition));
        try {
            jdbcTemplate.execute(updateSql.toString());
        } catch (DataAccessException e) {
            return BaseResponse.with(Status.PARAM_ERROR);
        }
        return BaseResponse.with(Status.SUCCESS);
    }

    @Override
    public PageInfo query(QueryRowRequest request) {
        String tableName = request.getTableName();
        String fieldName = request.getConditionField();
        String condition = request.getConditionValue();
        PageInfo pageInfo = request.getPageInfo();
        int pageNo = Integer.valueOf(pageInfo.getPageNo());
        int pageSize = Integer.valueOf(pageInfo.getPageSize());
        List<String> fieldNames = request.getFieldNames();
        String columnNames = String.join(",", fieldNames);
        StringBuilder querySql = new StringBuilder(String.format("SELECT %s FROM `%s` ", columnNames, tableName));
        if (fieldName != null && condition != null) {
            querySql.append(String.format("WHERE %s = '%s' ", fieldName, condition));
        }
        int offset = (pageNo - 1) * pageSize;
        querySql.append(String.format("LIMIT %s offset %s;", pageSize, offset));
        List<Map<String, Object>> res;
        try {
            res = jdbcTemplate.queryForList(querySql.toString());
        } catch (DataAccessException e) {
            return null;
        }
        pageInfo.setPageData(res);
        return pageInfo;
    }

    public long queryTableRowsNum(String tableName) {
        String Sql = "SELECT COUNT(*) AS cnt FROM " + tableName;
        Map<String, Object> res;
        try {
            res = jdbcTemplate.queryForMap(Sql);
        } catch (DataAccessException e) {
            return -1;
        }
        return (long) res.get("cnt");
    }

    public BaseResponse updateRowNum(String tableName) {
        long rowNum = queryTableRowsNum(tableName);
        UpdateRowRequest updateRowRequest = new UpdateRowRequest();
        updateRowRequest.setTableName("table_record");
        List<String> fieldNames = new ArrayList<String>(){{
            add("row_num");
        }};
        List<String> values = new ArrayList<String>(){{
            add(String.valueOf(rowNum));
        }};
        updateRowRequest.setFiledNames(fieldNames);
        updateRowRequest.setValues(values);
        updateRowRequest.setConditionField("name");
        updateRowRequest.setConditionValue(tableName);
        return update(updateRowRequest);
    }

}
