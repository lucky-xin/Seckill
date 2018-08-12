package com.xin.seckill.service;

import org.apache.ibatis.session.RowBounds;

import java.util.List;

/*
 * @author Luchaoxin
 * @version V1.0
 * @Description: 分页查询接口
 * @date 2018-08-12 16:45
 * @Copyright (C)2018 , Luchaoxin
 */
public interface PagingQuery<T> {

    List<T> queryData(int offset, int limit) throws Exception;

    default RowBounds getRowBounds(int offset, int limit) {
        return new RowBounds(offset, limit);
    }
}
