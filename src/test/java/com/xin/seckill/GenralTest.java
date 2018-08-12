package com.xin.seckill;

import com.google.common.base.CaseFormat;
import com.xin.seckill.pojo.Items;
import com.xin.seckill.util.DynamicSQLUtil;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: ces
 * @date 2018-08-12 11:29
 * @Copyright (C)2018 , Luchaoxin
 */
public class GenralTest {
    @Test
    public void test1() throws NoSuchFieldException {
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "createtime"));

        StringBuilder sql = new StringBuilder();
        String[] fields = new String[]{"id", "name", "price", "pic", "createtime", "detail"};
        Items record = new Items();
        for (String fieldName : fields) {
            Field field = record.getClass().getDeclaredField(fieldName);
            String tmp = "<if test=\"_field != null\"> AND _column=#{_field}</if>";
            sql.append(tmp.replaceAll("_field", field.getName()).replaceAll("_column", CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName())));
        }
//        System.out.println(sql.toString());

        System.out.println(DynamicSQLUtil.getAndStmt(Items.class));
    }
}
