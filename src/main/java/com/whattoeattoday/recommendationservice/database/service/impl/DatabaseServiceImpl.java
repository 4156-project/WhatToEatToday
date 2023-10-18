package com.whattoeattoday.recommendationservice.database.service.impl;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.database.request.table.BuildTableRequest;
import com.whattoeattoday.recommendationservice.database.request.table.DeleteTableRequest;
import com.whattoeattoday.recommendationservice.database.request.table.QueryTableRequest;
import com.whattoeattoday.recommendationservice.database.request.table.UpdateTableRequest;
import com.whattoeattoday.recommendationservice.database.response.QueryTableNamesResponse;
import com.whattoeattoday.recommendationservice.database.response.QueryTableResponse;
import com.whattoeattoday.recommendationservice.database.service.DatabaseService;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.*;

@Service
public class DatabaseServiceImpl implements DatabaseService {

    @Resource
    private JdbcTemplate jdbcTemplate;
    @Override
    public BaseResponse buildTable(BuildTableRequest request) {
        String tableName = request.getTableName();
        List<String> fieldNameList = request.getFieldNameList();
        List<String> fieldTypeList = request.getFieldTypeList();
        StringBuilder sqlBuilder = new StringBuilder(String.format("CREATE TABLE IF NOT EXISTS `%s`( ", tableName));
        for (int i = 0; i < fieldNameList.size(); i++) {
            String subSql = String.format("`%s` %s, \n", fieldNameList.get(i), fieldTypeList.get(i));
            sqlBuilder.append(subSql);
        }
        sqlBuilder.append(String.format(" PRIMARY KEY (`%s`))\n ENGINE=InnoDB DEFAULT CHARSET=utf8;\n", request.getPrimaryKey()));
        // Use try-catch to avoid failure to build table
        try {
            jdbcTemplate.execute(sqlBuilder.toString());
        } catch (DataAccessException e) {
            return BaseResponse.with(Status.PARAM_ERROR);
        }
        // Update table_record
        String columnNames = String.join(",", fieldNameList);
        String columnTypes = String.join(",", fieldTypeList);
        StringBuilder insertSql = new StringBuilder(String.format("INSERT IGNORE INTO `table_record` (name, column_names, column_types) VALUES ('%s', '%s', '%s')",
                tableName, columnNames, columnTypes));
        try {
            jdbcTemplate.execute(insertSql.toString());
        } catch (DataAccessException e) {
            return BaseResponse.with(Status.PARAM_ERROR);
        }

        return BaseResponse.with(Status.SUCCESS);
    }

    @Override
    public BaseResponse deleteTable(DeleteTableRequest request) {
        String tableName = request.getTableName();
        StringBuilder sqlBuilder = new StringBuilder(String.format("DROP TABLE IF EXISTS `%s`", tableName));
        try {
            jdbcTemplate.execute(sqlBuilder.toString());
        } catch (DataAccessException e) {
            return BaseResponse.with(Status.PARAM_ERROR);
        }

        // Update table_record
        StringBuilder deleteSql = new StringBuilder(String.format("DELETE FROM `table_record` WHERE name = '%s'", tableName));
        try {
            jdbcTemplate.execute(deleteSql.toString());
        } catch (DataAccessException e) {
            return BaseResponse.with(Status.PARAM_ERROR);
        }
        return BaseResponse.with(Status.SUCCESS);
    }

    @Override
    public QueryTableResponse queryTable(QueryTableRequest request) {
        String tableName = request.getTableName();
        StringBuilder sqlBuilder = new StringBuilder(String.format("SELECT id, name, row_num, column_names, column_types FROM `table_record` WHERE name = '%s'", tableName));
        Map<String, Object> table;
        try {
            table = jdbcTemplate.queryForMap(sqlBuilder.toString());
        } catch (DataAccessException e) {
            return null;
        }

        QueryTableResponse queryTableResponse = new QueryTableResponse();
        String[] columnNames = null;
        String[] columnTypes = null;
        for (String key : table.keySet()) {
            if ("id".equals(key)) {
                queryTableResponse.setId((BigInteger) table.get(key));
            } else if ("row_num".equals(key)) {
                queryTableResponse.setRowNum((Long) table.get(key));
            } else if ("column_names".equals(key)) {
                columnNames = ((String) table.get(key)).split(",");
            } else if ("column_types".equals(key)) {
                columnTypes = ((String) table.get(key)).split(",");
            } else if ("name".equals(key)) {
                queryTableResponse.setTableName((String) table.get(key));
            }
        }
        Map<String, String> nameTypeMap = new HashMap<>(10);
        for (int i = 0; i < Objects.requireNonNull(columnNames).length; i++) {
            assert columnTypes != null;
            nameTypeMap.put(columnNames[i], columnTypes[i]);
        }
        queryTableResponse.setFiledNameTypeMap(nameTypeMap);
        return queryTableResponse;
    }

    @Override
    public QueryTableNamesResponse queryTableNames() {
        String sql = "SELECT name FROM `table_record`";
        List<Map<String, Object>> result = null;

        try {
            result = jdbcTemplate.queryForList(sql);
        } catch (DataAccessException e) {
            return null;
        }

        Set<String> names = new HashSet<>();
        for (Map<String, Object> map : result) {
            names.add((String) map.get("name"));
        }
        QueryTableNamesResponse response = new QueryTableNamesResponse();
        response.setTableNames(names);
        return response;
    }

    @Override
    public BaseResponse setAutoIncrement(UpdateTableRequest request) {
        String tableName = request.getTableName();
        String columnName = request.getColumnName();
        String sql = "ALTER TABLE " + tableName + " MODIFY COLUMN " + columnName + " INT AUTO_INCREMENT;";
        try {
            jdbcTemplate.execute(sql);
        } catch (DataAccessException e) {
            return BaseResponse.with(Status.PARAM_ERROR);
        }
        return BaseResponse.with(Status.SUCCESS);
    }

    @Override
    public BaseResponse setUniqueKey(UpdateTableRequest request) {
        String tableName = request.getTableName();
        String columnName = request.getColumnName();
        String sql = "ALTER TABLE " + tableName + " ADD CONSTRAINT unique_" + columnName + " UNIQUE (" + columnName + ")";
        try {
            jdbcTemplate.execute(sql);
        } catch (DataAccessException e) {
            return BaseResponse.with(Status.PARAM_ERROR);
        }
        return BaseResponse.with(Status.SUCCESS);
    }

}
