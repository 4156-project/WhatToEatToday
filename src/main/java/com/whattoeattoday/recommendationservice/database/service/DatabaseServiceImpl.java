package com.whattoeattoday.recommendationservice.database.service;

import com.whattoeattoday.recommendationservice.common.BaseResponse;
import com.whattoeattoday.recommendationservice.common.Status;
import com.whattoeattoday.recommendationservice.database.request.BuildTableRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/15/23
 */
@Service
public class DatabaseServiceImpl implements DatabaseService{

    @Resource
    private JdbcTemplate jdbcTemplate;
    @Override
    public BaseResponse buildTable(BuildTableRequest request) {
        String tableName = request.getTableName();
        List<String> fieldNameList = request.getFieldNameList();
        List<String> fieldTypeList = request.getFieldTypeList();
        StringBuilder sqlBuilder = new StringBuilder(String.format("CREATE TABLE IF NOT EXISTS `%s`( ", tableName));
        List<String> fieldSql = new ArrayList<>();
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

        return BaseResponse.with(Status.SUCCESS);
    }
}
