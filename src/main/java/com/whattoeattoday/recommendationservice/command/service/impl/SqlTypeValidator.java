package com.whattoeattoday.recommendationservice.command.service.impl;

import java.math.BigDecimal;

/**
 * @author Yufeng Wan
 */
public class SqlTypeValidator {

    public static boolean isValid(Object value, String sqlType) {

        sqlType = sqlType.toLowerCase();

        if (value == null) {
            return true;
        }


        String[] typeParts = sqlType.split("\\(|\\)");
        String type = typeParts[0];
        String length = typeParts.length > 1 ? typeParts[1] : "";

        switch (type) {
            case "int":
            case "integer":
            case "tinyint":
            case "smallint":
            case "mediumint":
            case "bigint":
                return value instanceof Integer || value instanceof Long;
            case "varchar":
            case "char":
                if (value instanceof String && !length.isEmpty()) {
                    int maxLength = Integer.parseInt(length);
                    return ((String) value).length() <= maxLength;
                }
                return value instanceof String;
            case "float":
            case "double":
            case "decimal":
                return value instanceof Float || value instanceof Double || value instanceof BigDecimal;
            default:
                return false;
        }
    }

    public static void main(String[] args) {

        System.out.println(isValid(123, "int"));
        System.out.println(isValid("hello", "varchar(10)"));
        System.out.println(isValid("hello world", "varchar(5)"));
    }
}
