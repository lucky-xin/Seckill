package com.xin.seckill.util;

import com.google.common.base.CaseFormat;
import com.xin.utils.StringUtil;

import java.lang.reflect.Field;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 动态sql工具类
 * @date 2018-08-12 11:52
 * @Copyright (C)2018 , Luchaoxin
 */
public class DynamicSQLUtil {

    /**
     * 根据成员属相生成sql更新语句
     * 注意：使用驼峰式命名时，字段名必须符合以下规则
     * 如：字段名称为 userName 则数据库字段为 user_name
     *
     * @param fields 成员名称数组
     * @param clazz  po类
     * @return sql语句
     * @throws NoSuchFieldException
     */
    public static String getAndStmt(String fields[], Class<?> clazz) throws NoSuchFieldException {
        StringBuilder sql = new StringBuilder();
        for (String fieldName : fields) {
            Field field = clazz.getDeclaredField(fieldName);
            String tmp = "<if test=\"_field != null\"> AND _column=#{_field}</if>";
            sql.append(tmp.replaceAll("_field", field.getName()).replaceAll("_column",
                    CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName())));
        }
        return sql.toString();
    }

    public static String getAndStmt(Class<?> clazz) throws NoSuchFieldException {
        Field[] declaredFields = clazz.getDeclaredFields();
        String[] fields = new String[declaredFields.length];
        for (int i = 0; i < declaredFields.length; i++) {
            fields[i] = declaredFields[i].getName();
        }
        return getAndStmt(fields, clazz);
    }

    public static String getInStmt(String column, Object[] values) {
        if (StringUtil.isEmpty(column) || values.length == 0) {
            return "";
        }
        StringBuilder sql = new StringBuilder(" ").append(column).append(" in ( ");
        for (int i = 0; i < values.length; i++) {
            sql.append("'").append(values[i]).append("'");
            if (values.length - 1 != i) {
                sql.append(",");
            } else {
                sql.append(")");
            }
        }
        return sql.toString();
    }

    public static String getInCondition(String column, Object[] values) {
        return " and " + getInStmt(column, values);
    }

    public static String getLikeCondition(String column, Object value) {
        return " and " + getLikeStmt(column, value);
    }

    public static String getLikeStmt(String column, Object value) {
        return new StringBuilder(" ").append(column).append(" like '%").append(value).append("%'").toString();
    }

    public static String getOrCondition(String column, Object value) {
        return new StringBuilder(" or ").append(column).append(" = ").append(value).toString();
    }


}
