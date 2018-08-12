package com.xin.seckill.mybatis.handlers;

import com.xin.seckill.enums.Sex;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 性别类型处理器
 * @date 2018-08-11 23:45
 * @Copyright (C)2018 , Luchaoxin
 */
public class SexValueTypeHandler implements TypeHandler<Object> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        if (parameter instanceof Sex) {
            Sex sex = (Sex) parameter;
            ps.setInt(i, sex.getCode());
        }
    }

    @Override
    public Object getResult(ResultSet rs, String columnName) throws SQLException {
        int index = rs.getInt(columnName);
        if ("sex".equals(columnName)) {
            return Sex.getSex(index);
        }
        return rs.getRef(index);
    }

    @Override
    public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
        int index = rs.getInt(columnIndex);
        if (rs.findColumn("sex") == index) {
            return Sex.getSex(index);
        }
        return rs.getRef(columnIndex);
    }

    @Override
    public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {

        if (cs.getResultSet().findColumn("sex") == columnIndex) {
            return Sex.getSex(cs.getInt(columnIndex));
        }
        return cs.getRef(columnIndex);
    }
}
