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
 * @Description: 性别映射处理器
 * @date 2018-08-11 21:39
 * @Copyright (C)2018 , Luchaoxin
 */
public class SexEnumTypeHandler implements TypeHandler<Sex> {

    @Override
    public void setParameter(PreparedStatement ps, int index, Sex parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(index, parameter.getCode());
    }

    @Override
    public Sex getResult(ResultSet rs, String columnName) throws SQLException {
        int index = rs.getInt(columnName);
        return Sex.getSex(index);
    }

    @Override
    public Sex getResult(ResultSet rs, int columnIndex) throws SQLException {
        int index = rs.getInt(columnIndex);
        return Sex.getSex(index);
    }

    @Override
    public Sex getResult(CallableStatement cs, int columnIndex) throws SQLException {
        int index = cs.getInt(columnIndex);
        return Sex.getSex(index);
    }
}
