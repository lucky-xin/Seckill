package com.xin.seckill.exception;

import com.xin.seckill.enums.SeckillStatEnum;
import com.xin.seckill.pojo.SuccessKilled;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 秒杀操作异常
 * @date 2018-08-10 16:24
 * @Copyright (C)2018 , Luchaoxin
 */

public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeckillException(long seckillId, SeckillStatEnum success, SuccessKilled successKilled) {
    }
}