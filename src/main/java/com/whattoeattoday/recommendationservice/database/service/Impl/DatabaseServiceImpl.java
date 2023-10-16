package com.whattoeattoday.recommendationservice.database.service.Impl;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.database.request.BuildTableRequest;
import com.whattoeattoday.recommendationservice.database.request.QueryTableRequest;
import com.whattoeattoday.recommendationservice.database.service.DatabaseService;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
        StringBuilder insertSql = new StringBuilder(String.format("INSERT INTO `table_record` (name, column_names, column_types) VALUES ('%s', '%s', '%s')",
                tableName, columnNames, columnTypes));
        try {
            jdbcTemplate.execute(insertSql.toString());
        } catch (DataAccessException e) {
            return BaseResponse.with(Status.PARAM_ERROR);
        }

        return BaseResponse.with(Status.SUCCESS);
    }

    @Override
    public BaseResponse deleteTable(QueryTableRequest request) {
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
    public BaseResponse queryTable(QueryTableRequest request) {
        String tableName = request.getTableName();
        StringBuilder sqlBuilder = new StringBuilder(String.format("SELECT id, name, row_num, column_names, column_types FROM `table_record` WHERE name = '%s'", tableName));
        Map<String, Object> table;
        try {
            table = jdbcTemplate.queryForMap(sqlBuilder.toString());
        } catch (DataAccessException e) {
            return BaseResponse.with(Status.PARAM_ERROR, null, null);
        }
        return BaseResponse.with(Status.SUCCESS, table);
    }

}
