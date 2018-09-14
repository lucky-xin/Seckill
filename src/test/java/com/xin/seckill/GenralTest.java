package com.xin.seckill;

import com.google.common.base.CaseFormat;
import com.xin.seckill.pojo.Items;
import com.xin.seckill.pojo.UserInfo;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "createTime"));

        StringBuilder sql = new StringBuilder();
        String[] fields = new String[]{"id", "name", "price", "pic", "createtime", "detail"};
        Items record = new Items();
        for (String fieldName : fields) {
            Field field = record.getClass().getDeclaredField(fieldName);
            String tmp = "<if test=\"_field != null\"> AND _column=#{_field}</if>";
            sql.append(tmp.replaceAll("_field", field.getName()).replaceAll("_column", CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName())));
        }
//        System.out.println(sql.toString());

//        System.out.println(DynamicSQLUtil.getUpdateStmt(Items.class, "id"));
        UserInfo user = new UserInfo();
        user.setName("jhjkh");

        String fieldName = "name";
        String firstChar = String.valueOf(fieldName.charAt(0));
        String getMethodName = "get" + fieldName.replaceFirst(firstChar, firstChar.toUpperCase());
        Class<?> clazz = user.getClass();

        try {
            Method getMethod = clazz.getMethod(getMethodName);
            Object value = getMethod.invoke(user);
            System.out.println(value);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }


    public static Object getGetMethod(Object ob, String name) throws Exception {
        Method[] m = ob.getClass().getMethods();
        for (int i = 0; i < m.length; i++) {
            if (("get" + name).toLowerCase().equals(m[i].getName().toLowerCase())) {
                return m[i].invoke(ob);
            }
        }
        return null;
    }

    @Test
    public void test3() {
        int n = 9;
        int i = 6;
        n = n++ % --i;
        System.out.println(n);
    }
}
