package com.whattoeattoday.recommendationservice.common;

import com.whattoeattoday.recommendationservice.database.request.table.QueryTableRequest;
import com.whattoeattoday.recommendationservice.database.response.QueryTableResponse;
import com.whattoeattoday.recommendationservice.database.service.DatabaseService;
import com.whattoeattoday.recommendationservice.database.service.impl.DatabaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lijie Huang lh3158@columbia.edu
 * @date 10/13/23
 */
@Component
public class ParamUtil {

    private static DatabaseService databaseService;

    @Autowired
    public ParamUtil (DatabaseService databaseService) {
        ParamUtil.databaseService = databaseService;
    }

    static final Pattern NUMERIC_PATTERN = Pattern.compile("[0-9]*");
    public static boolean isBlank(String param) {
        return param == null || param.isEmpty();
    }

    public static boolean isAllNotBlank(String[] params) {
        for (String param : params) {
            if (param == null || param.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumeric(String str) {
        Matcher isNum = NUMERIC_PATTERN.matcher(str);
        return isNum.matches();
    }

    public static boolean isFiledNames(String tableName, List<String> fieldNames) {
        QueryTableRequest request = new QueryTableRequest();
        request.setTableName(tableName);
        QueryTableResponse response = databaseService.queryTable(request);
        Map<String, String> fieldNameTypeMap = response.getFiledNameTypeMap();
        for (String fieldName : fieldNames) {
            if (!fieldNameTypeMap.containsKey(fieldName)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPageValid(String pageNo, String pageSize) {
        return isNumeric(pageNo) && isNumeric(pageSize);
    }

    public static boolean isTableName(String tableName) {
        return databaseService.queryTableNames().getTableNames().contains(tableName);
    }
}