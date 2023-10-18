package com.whattoeattoday.recommendationservice.common;

import com.whattoeattoday.recommendationservice.database.request.table.QueryTableRequest;
import com.whattoeattoday.recommendationservice.database.response.QueryTableResponse;
import com.whattoeattoday.recommendationservice.database.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
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



    public static boolean isTypeValid(String type) {
        String baseType = type.split("\\(")[0].toUpperCase();
        String param = null;
        if (baseType.length() != type.length()) {
            if (type.charAt(type.length() - 1) != ')') {return false;}
            if (type.length() - baseType.length() <= 2) {return false;}
            param = type.substring(baseType.length()+1, type.length()-1);
        }
        switch (baseType) {
            case "CHAR":
            case "BINARY":
            case "TINYINT":
            case "SMALLINT":
            case "MEDIUMINT":
            case "INT":
            case "INTEGER":
            case "BIGINT":
                return param == null || checkIntRange(param, 0, 255);
            case "VARCHAR":
            case "VARBINARY":
                return param != null && checkIntRange(param, 0, 65535);
            case "ENUM":
            case "SET":
                if (param == null) {return false;}
                String[] paramArray = param.split(",");
                for (String p : paramArray) {
                    p = p.trim();
                   if (p.isEmpty()) {return false;}
                   if (p.charAt(0) != '\'' || p.charAt(p.length()-1) != '\'' || p.length() <=2) {return false;}
                }
            case "BIT":
                return param == null || checkIntRange(param, 1, 64);
            case "FLOAT":
                return param == null || checkIntRange(param, 1, 53);
            case "DECIMAL":
            case "DEC":
                if (param == null) {return true;}
                String[] paramArray2 = param.split(",");
                if (paramArray2.length == 1) {
                    return checkIntRange(paramArray2[0], 1, 65);
                } else if (paramArray2.length == 2) {
                    if (paramArray2[0].trim().isEmpty()) {
                        return false;
                    } else {
                        return checkIntRange(paramArray2[0], 1, 65) && (paramArray2[1].trim().isEmpty() || checkIntRange(paramArray2[0], 0, 30));
                    }
                }
            case "DATETIME":
            case "TIMESTAMP":
            case "TIME":
                return param == null || checkIntRange(param, 0, 6);
            case "TINYBLOB":
            case "TINYTEXT":
            case "TEXT":
            case "BLOB":
            case "MEDIUMTEXT":
            case "MEDIUMBLOB":
            case "LONGTEXT":
            case "LONGBLOB":
            case "BOOL":
            case "BOOLEAN":
            case "DOUBLE":
            case "DOUBLE PRECISION":
            case "DATE":
            case "YEAR":
                return param == null;
            default:
                return false;
        }
    }

    private static boolean checkIntRange(String value, int min, int max) {
        try {
            int intValue = Integer.parseInt(value.trim());
            return intValue >= min && intValue <= max;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean isTableName(String tableName) {
        return databaseService.queryTableNames().getTableNames().contains(tableName);
    }
}
