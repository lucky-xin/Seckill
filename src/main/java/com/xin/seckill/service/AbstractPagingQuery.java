package com.xin.seckill.service;

import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: PagingQuery抽象类
 * @date 2018-08-12 16:47
 * @Copyright (C)2018 , Luchaoxin
 */
public abstract class AbstractPagingQuery<T> implements PagingQuery<T> {

    @Override
    public List<T> queryData(int offset, int limit) throws Exception {
        return doPagingQuery(getRowBounds(offset, limit));
    }

    protected abstract List<T> doPagingQuery(RowBounds rowBounds) throws Exception;
}
