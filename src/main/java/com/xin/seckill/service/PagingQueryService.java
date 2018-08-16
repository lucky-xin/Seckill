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
public interface PagingQueryService<T> {

    default List<T> pagingQuery(int offset, int limit) throws Exception {
        return doPagingQuery(getRowBounds(offset, limit));
    }

    default RowBounds getRowBounds(int offset, int limit) {
        return new RowBounds(offset, limit);
    }

    List<T> doPagingQuery(RowBounds rowBounds) throws Exception;

}
